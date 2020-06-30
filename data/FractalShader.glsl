uniform float time;
uniform vec2 resolution;
uniform sampler2D fractalTexture;
uniform vec2 mouse;
/*
        Copyright (c) 2019 Joshua Dyer

		Special thanks to The Art of Code (https://www.youtube.com/watch?v=il_Qg9AqQkE) for the basis of this shader.

		I modified it some for my own understanding and to add parameters for playing around with it.
		Any errors/bugs in the code (or comments) are my own.
		Shader Variables (Params) are found at the top of mainImage. Feel free to play around with them.
		Comments may or may not make 100% sense. Watch the video linked above for a better understanding of the shader.

		Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
		files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
		modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
		Software is furnished to do so, subject to the following conditions:

		The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
		Software.


		THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
		WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
		COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
		ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/



//Calculate normal vector.
vec2 N(float angle) {
    return vec2(sin(angle), cos(angle));
}

//Rotate around an arbitrary line by a given angle.
vec2 rotate(vec2 uv, vec2 cp, float a, bool side) {
    vec2 n = N(a * 3.14159);
    float d = dot(uv - cp, n);
    if (side) {
        uv -= n * max(0.0, d) * 2.0;
    } else {
        uv -= n * min(0.0, d) * 2.0;
    }
    return uv;
}

//Used if needing the distance for showing the mirroring line.
float dist(vec2 uv, vec2 cp, float a) {
    vec2 n = N(a * 3.14159);
    return dot(uv - cp, n);
}

void main()
{
    /* Shader Variables */
    int iterations = 35;//Number of fractal iterations.
    float thickness = 2.0;//Thickness of the lines to be drawn.
    bool mouseOn = false;//Should click dragging affect the rotation?
    bool trippyInAndOut = true;//Should the image trip in and out over time?
    bool trippyTexture = true;//Should the image use the texture in iChannel0, changing over time?
    bool showUVFolding = false;//Use primary colors to show UV folding/mirroring?
    /* End Shader Variables */

    if (iterations < 1) {
        iterations = 1;
    }

    if (thickness <= 0.0) {
        thickness = 1.0;
    }

    vec2 uv = (gl_FragCoord.xy - 0.5 * resolution.xy) / resolution.y;//Center Origin, Remap to -0.5 to +0.5, and square using aspect ratio.

    vec2 mouse = mouse.xy / resolution.xy;//Useful for finding exact mirroring angles to use, just place mouse.x in place of angle
    if (!mouseOn) {
        mouse = vec2(1.0);//If not using mouse, lock to 1.0, 1.0 (because it is multiplied by a value below.)
    }

    uv *= 1.25; //Zoom out.
    uv.y += tan((5.0 / 6.0) * 3.14159) * 0.5;// Re-Center

    vec3 col = vec3(0);//Set all black.

    uv.x = abs(uv.x);//Mirror on Y axis.

    uv = rotate(uv, vec2(0.5, 0.0), 5.0 / 6.0, true);//Rotate UV around a line passing through (0.5, 0.0) by (5.0 / 6.0) angle.

    //col += smoothstep(0.01, 0.0, abs(dist(uv, vec2(0.5, 0.0), mouse.x)));//Show the mirroring line.

    float scale = 1.0;//Set initial scale.
    uv.x += 0.5;//Shift right by 1 /2 unit.
    for (int i = 0; i < iterations; i++) {//Loop through the number of iterations for the fractal.
        uv *= 3.0;//Scale UV space by a factor of 3
        scale *= 3.0;//Keep track of total scale change.
        uv.x -= 1.5;//Shift left by 1.5 Units

        uv.x = abs(uv.x);//Mirror on Y axis

        uv.x -= 0.5;//Shift left by 1/2 Unit
        uv = rotate(uv, vec2(0.0, 0.0), mouse.y * 2.0 / 3.0, false);//Fold to create mirrored rotated segments. (The ^ part.)
    }

    if (trippyInAndOut) {
        //For trippy effect.
        uv *= cos(time * 0.5);
        uv = rotate(uv, vec2(0.0, 0.0), cos(time / 13.), false);
    }

    if (trippyTexture) {
        //Very trippy effect!
        uv /= scale;
        col += texture(fractalTexture, uv * 2.0 - time * 0.1).rgb;
        //col += texture(fractalTexture, fract(uv * 2.0 - time * 0.1)).rgb;
    } else {
        //Calculate the color based on the distance from the line. Until now, just shifting, scaling, mirroring UV space.
        //Remember uv space has been mirrored repeatedly to create the fractal outline.
        //So we are only drwaing one line, but it is crumpled up.
        float d = length(uv - vec2(clamp(uv.x, -1.0, 1.0), 0));
        col += smoothstep(thickness / resolution.y, 0.0, d / scale);//Smooth out and thicken the lines. and adjust based on scale.
    }

    if (showUVFolding) {
        col.rg += uv;//Demonstrate UV space folding/mirroring.
    }

    gl_FragColor = vec4(col, 1.0);//Output the color.
}