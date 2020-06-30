uniform float time;
uniform vec2 resolution;


float Xor(float a, float b) {
    return a * (1.0 - b) + b * (1.0 - a);
}

float PI = 3.14159265359;

float map(float value, float min1, float max1, float min2, float max2) {
    return min2 + (value - min1) * (max2 - min2) / (max1 - min1);
}

void main() {

    vec2 uv = (gl_FragCoord.xy - 0.5 * resolution.xy) / resolution.y;
    float a = PI / 4.0;
    float s = sin(a);
    float c = cos(a);

    uv *= mat2(c, -s, s, c);
    uv *= 15.0;
    vec3 col = vec3(0);
    vec2 gv = fract(uv) - 0.5;
    vec2 id = floor(uv);
    float m = 0.;
    float t = time;
    for(float y = -1.0; y <= 1.0; y++) {
        for(float x = -1.0; x <= 1.0; x++) {
            vec2 offset = vec2(x, y);

            float d = length(gv - offset);
            float dist = length(id + offset) * .3;
            float r = mix(.3, 1.5, sin(dist - t) * .5 + .5);
           // float mappedValue = map(sin(time), -1, 1, 0.85, cos(time));
            m = Xor(m, smoothstep(r, r * 0.9, d));

        }
    }

    //col.rg = gv;
    col += m;
    col *= vec3(abs(sin(time)), abs(cos(time)), 0.4);
    float limit = map(sin(time), -1, 1, 0.05, 0.75);
    col *= limit;
    gl_FragColor = vec4(col, 1.0);
}