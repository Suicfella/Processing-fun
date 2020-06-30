uniform vec2 resolution;
uniform float time;

#define rot(j) mat2(cos(j), -sin(j), sin(j), cos(j))

float sdCircle(vec2 p, float r) {
 //   p -= vec2(r + 0.5, 0.5);

    return length(p) - r;
}
float line(in vec2 p, vec2 a, vec2 b) {
    vec2 pa = p - a;
    vec2 ba = b - a;
    float h = clamp(dot(pa, ba)/dot(ba, ba), 0., 1.);
    return length(pa - ba*h);
}



void main() {
    vec2 uv = (gl_FragCoord.xy - 0.5 *  resolution.xy) / resolution.y;
    float t = time;
    //uv *= 2.;
    // uv *= rot(time);
    vec3 col = vec3(0);
  //  uv.x = fract(uv.x);
   // uv.y = fract(uv.y);
    uv = fract(uv*16.) - 0.5;
    float d = 10e6;
    d = min(d, line(vec2(uv.x + 0.1, uv.y + 0.2), vec2(-0.5, 0.2), vec2(0.6, 0.2)));
    d -= 0.01;
    col += smoothstep(0.02, 0., d);
    col *= vec3(1, 1, 1);



    d = min(d, sdCircle(vec2(uv.x + 0.05, uv.y), 0.15));
    col += smoothstep(0.003, 0., d);




    gl_FragColor = vec4(col, 1);
}