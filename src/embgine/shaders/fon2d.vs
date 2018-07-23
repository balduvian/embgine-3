#version 330 core

layout (location = 0) in vec3 vertices;
layout (location = 1) in vec2 iTexCoords;

uniform mat4 mvp;
uniform int character; 

out vec2 texCoords;

void main() {
	
	int cx = character % 16;
	int cy = character/16;
	
	//vec4 frame = vec4((1/16f), (1/8f), cx*(1/16f), cy*(1/8f));
	
	vec4 frame = vec4((1/16), (1/8), cx*(1/16), cy*(1/8));
	
	texCoords = iTexCoords * frame.xy + frame.zw;
    gl_Position = mvp*vec4(vertices, 1);
    
}