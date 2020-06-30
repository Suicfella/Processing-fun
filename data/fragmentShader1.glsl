uniform sampler2D textureImg;
uniform vec2 resolution;
uniform float time;

void main(){
vec2 uv = gl_FragCoord.xy / resolution.xy;
uv *= fract(time);
gl_FragColor = texture2D(textureImg, uv);
}