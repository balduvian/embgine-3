#version 330 core

uniform sampler2D sampler;

uniform float rand;
uniform float scanline;

in vec2 texCoords;

in vec2 normalized;

out vec4 color;

float random(float x, float y){
    return fract(sin(dot(vec2(x, y) , vec2(12.9898,78.233))) * 43758.5453);
}

void main() {

	float y = float(gl_FragCoord.y);
	
	float pos = mod (y, 2);
	
	float rOffX = random(rand, 0) / 900;
	float rOffY = random(rand, 1) / 900;
	float gOffX = random(rand, 2) / 900;
	float gOffY = random(rand, 3) / 900;
	float bOffX = random(rand, 4) / 900;
	float bOffY = random(rand, 5) / 900;
	
	float scanOff = 0.1 / ( 1 + abs(16 * (scanline - normalized.y)) ) - 0.05 - (random(rand, 6) * 0.025);
	
	if(scanOff < 0) {
		scanOff = 0;
	}
	
	float scanBright = scanOff * 2;
	
	if( pos > 0.5 ) {
		
		float lineOff = random(rand, y) / 900;
		
		float r = texture(sampler, texCoords + vec2( lineOff + rOffX - scanOff, rOffY)).x + scanBright;
		float g = texture(sampler, texCoords + vec2( lineOff + gOffX - scanOff, gOffY)).y + scanBright;
		float b = texture(sampler, texCoords + vec2( lineOff + bOffX - scanOff, bOffY)).z + scanBright;
		
		color = vec4(r, g, b, 1);
		
	} else {
	
		float r = texture(sampler, texCoords + vec2( rOffX - scanOff, rOffY)).x + scanBright - 0.1;
		float g = texture(sampler, texCoords + vec2( gOffX - scanOff, gOffY)).y + scanBright - 0.1;
		float b = texture(sampler, texCoords + vec2( bOffX - scanOff, bOffY)).z + scanBright - 0.1;
		
		color = vec4(r, g, b, 1);
		
	}
	
}