

uniform float time, red, green, blue;
uniform vec2 resolution;

#define t time / 2.
#define r resolution.xy

float map(float value, float min1, float max1, float min2, float max2) {
    return min2 + (value - min1) * (max2 - min2) / (max1 - min1);
}

void main() {

    vec3 c;
    float l,z=t;
    float mappedIncrement = map(abs(sin(t)), 0, 1, 0.01, 0.1);
    float mappedOffset = map(r.x, 0, 800., 0, 0.2);
    for(int i=0;i<3;i++) {
        vec2 uv,p=gl_FragCoord.xy/r;
        uv=p;
        p-= .5;
        p.x*=r.x/r.y;
        z+=.5;
        l=length(p);
        uv+=p/l*(sin(z)+1.)*abs(sin(l*9.-z*2.));
        c[i]=mappedIncrement/length(abs(mod(uv,1)-.5));
        c *= vec3(0.9, .5, 0.3);
        l /= 0.025;
    }
   // c *= vec3(red, green, blue);
    gl_FragColor=vec4(c/l,t);

}
