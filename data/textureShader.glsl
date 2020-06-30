uniform float time;
uniform sampler2D firstTexture, secondTexture, thirdTexture;
uniform vec2 resolution;

void main() {

    vec2 uv = gl_FragCoord.xy / resolution.xy;
    uv.y = 1-uv.y;
    uv.y += time / 3.;
    uv.y = mod(uv.y, 3.);
    vec3 col;
    if(uv.y < 1){
        col = texture2D(firstTexture, uv, 1.).rgb;
    } else if(uv.y < 2){
        col = texture2D(secondTexture, uv, 1.).rgb;
    } else if(uv.y < 3){
        col = texture2D(thirdTexture, uv, 1.).rgb;
    }
    gl_FragColor = vec4(col, 1.);




    gl_FragColor = vec4(col, 1.);

}