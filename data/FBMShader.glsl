uniform float time;
uniform vec2 resolution;

float random (in vec2 st) {
    return fract(sin(dot(st.xy,
    vec2(12.9898, 78.233)))*
    43758.5453123);
}

float map(float value, float min1, float max1, float min2, float max2) {
    return min2 + (value - min1) * (max2 - min2) / (max1 - min1);
}

// Based on Morgan McGuire @morgan3d
// https://www.shadertoy.com/view/4dS3Wd



float noise (in vec2 st) {
    vec2 i = floor(st);
    vec2 f = fract(st);

    // Four corners in 2D of a tile
    float a = random(i);
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));

    vec2 u = f * f * (3.0 - 2.0 * f);

    return mix(a, b, u.x) +
    (c - a)* u.y * (1.0 - u.x) +
    (d - b) * u.x * u.y;
}

float OCTAVES = 6;
float fbm (in vec2 st) {
    // Initial values
    float value = 0.0;
    float amplitude = .5;
    float frequency = 0.;
    // Loop of octaves
    for (int i = 0; i < OCTAVES; i++) {
        value += amplitude * noise(st);
        st *= 2.;
        amplitude *= .5;
    }
    return value;
}

float f(float x, float y){
    float tmp=fbm(vec2(x,y));

    tmp=fbm(vec2(x+234*tmp,y+234*tmp));
    tmp=fbm(vec2(x+264*tmp,y+264*tmp));
    return tmp * tmp;
}



void main() {
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = (gl_FragCoord.xy - 0.5 * resolution.xy) / resolution.y;
    //uv *= fract(time);
    uv.x *= resolution.x/resolution.y;
    // Time varying pixel color
    vec3 col = 0.5 + 0.5*cos(time+uv.xyx+vec3(0, 2, 4));
    col += fbm(uv * (5 * time) + fbm(uv * (5 * time)));
    uv *= 1000. + time;
    //col = vec3(fbm(vec2(f(vec2(uv.x, uv.y)))));


    // Output to screen
    //  col *= vec3(1., 0.45, 1.);
    //col *= vec3(0.1, 0.6, 0.9);
    col *= .4;
    gl_FragColor = vec4(col, 1.0);
}