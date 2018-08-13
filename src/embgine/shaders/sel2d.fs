#version 330 core

uniform sampler2D sampler;

uniform float rand;
uniform float enabled;

in vec2 texCoords;

out vec4 color;

float random(float x, float y){
    return fract(sin(dot(vec2(x, y) , vec2(12.9898,78.233))) * 43758.5453);
}

void main() {

	if(enabled == 1){
	
		float y = float(gl_FragCoord.y);
		
		float pos = mod (y, 2);
		
		float lineOff = random(rand, y) / 32;
		
		color = texture(sampler, texCoords * vec2(1.5, 1.5) - vec2(0.25, 0.25) + vec2( lineOff, 0 ));
		
		if(color.w > 0 && color.x > 0 && color.y > 0 && color.z > 0) {
			color = vec4( 1, 1, 1, 1 );	
		}
		
	} else {
	
		color = texture(sampler, texCoords * vec2(1.5, 1.5) - vec2(0.25, 0.25));
		
	}
	
}