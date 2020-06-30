package processing;

import processing.core.PApplet;
import processing.opengl.PShader;
import processing.core.PGraphics;

import java.io.File;
import java.util.ArrayList;

import static java.lang.System.*;


public class ShaderSketch extends PApplet {

    private ArrayList<ShaderSnapshot> snapshots = new ArrayList<ShaderSnapshot>();
    private int shaderRefreshRateInMillis = 36;

    public PShader uniform(String fragPath) {
        ShaderSnapshot snapshot = findSnapshotByPath(fragPath);
        snapshot = initIfNull(snapshot, fragPath, null);
        return snapshot.compiledShader;
    }

    public PShader uniform(String fragPath, String vertPath) {
        ShaderSnapshot snapshot = findSnapshotByPath(fragPath);
        snapshot = initIfNull(snapshot, fragPath, vertPath);
        return snapshot.compiledShader;
    }

    public void hotFilter(String path, PGraphics canvas) {
        hotShader(path, null, true, canvas);
    }

    public void hotFilter(String path) {
        hotShader(path, null, true, g);
    }

    public void hotShader(String fragPath, String vertPath, PGraphics canvas) {
        hotShader(fragPath, vertPath, false, canvas);
    }

    public void hotShader(String fragPath, String vertPath) {
        hotShader(fragPath, vertPath, false, g);
    }

    public void hotShader(String fragPath, PGraphics canvas) {
        hotShader(fragPath, null, false, canvas);
    }

    public void hotShader(String fragPath) {
        hotShader(fragPath, null, false, g);
    }

    private void hotShader(String fragPath, String vertPath, boolean filter, PGraphics canvas) {
        ShaderSnapshot snapshot = findSnapshotByPath(fragPath);
        snapshot = initIfNull(snapshot, fragPath, vertPath);
        snapshot.update(filter, canvas);
    }

    private ShaderSnapshot initIfNull(ShaderSnapshot snapshot, String fragPath, String vertPath) {
        if (snapshot == null) {
            snapshot = new ShaderSnapshot(fragPath, vertPath);
            snapshots.add(snapshot);
        }
        return snapshot;
    }

    private ShaderSnapshot findSnapshotByPath(String path) {
        for (ShaderSnapshot snapshot : snapshots) {
            if (snapshot.fragPath.equals(path)) {
                return snapshot;
            }
        }
        return null;
    }

    private class ShaderSnapshot {
        String fragPath;
        String vertPath;
        File fragFile;
        File vertFile;
        PShader compiledShader;
        long fragLastKnownModified, vertLastKnownModified, lastChecked;
        boolean compiledAtLeastOnce = false;
        long lastKnownUncompilable = -shaderRefreshRateInMillis;


        ShaderSnapshot(String fragPath, String vertPath) {
            if (vertPath != null) {
                compiledShader = loadShader(fragPath, vertPath);
                vertFile = dataFile(vertPath);
                vertLastKnownModified = vertFile.lastModified();
                if (!vertFile.isFile()) {
                    println("Could not find shader at " + vertFile.getPath());
                }
            } else {
                compiledShader = loadShader(fragPath);
            }
            fragFile = dataFile(fragPath);
            fragLastKnownModified = fragFile.lastModified();
            lastChecked = currentTimeMillis();
            if (!fragFile.isFile()) {
                println("Could not find shader at " + fragFile.getPath());
            }
            this.fragPath = fragPath;
            this.vertPath = vertPath;
        }

        @SuppressWarnings("ManualMinMaxCalculation")
        long max(long a, long b) {
            if (a > b) {
                return a;
            }
            return b;
        }

        void update(boolean filter, PGraphics pg) {
            long currentTimeMillis = currentTimeMillis();
            long lastModified = fragFile.lastModified();
            if (vertFile != null) {
                lastModified = max(lastModified, vertFile.lastModified());
            }
            if (compiledAtLeastOnce && currentTimeMillis < lastChecked + shaderRefreshRateInMillis) {
//                println("compiled at least once, not checking, standard apply");
                applyShader(compiledShader, filter, pg);
                return;
            }
            if (!compiledAtLeastOnce && lastModified > lastKnownUncompilable) {
//                println("first try");
                tryCompileNewVersion(filter, pg, lastModified);
                return;
            }
            lastChecked = currentTimeMillis;
            if (lastModified > fragLastKnownModified && lastModified > lastKnownUncompilable) {
//                println("file changed, repeat try");
                tryCompileNewVersion(filter, pg, lastModified);
            } else if (compiledAtLeastOnce) {
//                println("file didn't change, standard apply");
                applyShader(compiledShader, filter, pg);
            }
        }

        private void applyShader(PShader shader, boolean filter, PGraphics pg) {
            if (filter) {
                pg.filter(shader);
            } else {
                pg.shader(shader);
            }
        }

        private void tryCompileNewVersion(boolean filter, PGraphics pg, long lastModified) {
            try {
                PShader candidate;
                if (vertFile == null) {
                    candidate = loadShader(fragPath);
                } else {
                    candidate = loadShader(fragPath, vertPath);
                }
                // we need to call filter() or shader() here in order to catch any compilation errors and not halt
                // the sketch
                applyShader(candidate, filter, pg);
                compiledShader = candidate;
                compiledAtLeastOnce = true;
                fragLastKnownModified = lastModified;
            } catch (Exception ex) {
                lastKnownUncompilable = lastModified;
                println("\n" + fragFile.getName() + ": " + ex.getMessage());
            }
        }
    }
}