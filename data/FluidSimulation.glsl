// wyattflanders.com/MeAndMyNeighborhood.pdf


uniform vec2 resolution, mouse;
uniform float time;
uniform sampler2D imgTex;
#define LOOKUP(COORD) texture(imgTex, (COORD)/resolution.xy)

vec4 Field (vec2 position) {
    // Rule 1 : All My Energy transates with my ordered Energy
    vec2 velocityGuess = LOOKUP (position).xy;
    vec2 positionGuess = position - velocityGuess;
    return LOOKUP (positionGuess);
}


void mainImage() // i give up
{
    vec4 color;
    Energy  =  Field(Me);
    // Neighborhood :
    vec4 pX  =  Field(Me + vec2(1, 0));
    vec4 pY  =  Field(Me + vec2(0, 1));
    vec4 nX  =  Field(Me - vec2(1, 0));
    vec4 nY  =  Field(Me - vec2(0, 1));

    // Rule 2 : Disordered Energy diffuses completely :
    Energy.b = (pX.b + pY.b + nX.b + nY.b)/4.0;

    // Rule 3 : Order in the disordered Energy creates Order :
    vec2 Force;
    Force.x = nX.b - pX.b;
    Force.y = nY.b - pY.b;
    Energy.xy += Force/4.0;

    // Rule 4 : Disorder in the ordered Energy creates Disorder :
    Energy.b += (nX.x - pX.x + nY.y - pY.y)/4.;

    // Gravity effect :
    Energy.y -= Energy.w/300.0;

    // Mass concervation :
    Energy.w += (nX.x*nX.w-pX.x*pX.w+nY.y*nY.w-pY.y*pY.w)/4.;

    //Boundary conditions :
    if (Me.x<10.||Me.y<10.||resolution.x-Me.x<10.||resolution.y-Me.y<10.)
    {
        Energy.xy *= 0.;
    }

    // Mouse input  :
    if (resolution.x > 0. && length(Me-resolution.xy) < 10.) {
        Energy.w = 1.;
    }

    color = LOOKUP (gl_FragCoord).wwww;

    gl_FragColor = color;
}