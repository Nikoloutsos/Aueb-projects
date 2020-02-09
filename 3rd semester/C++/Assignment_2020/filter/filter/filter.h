#pragma once
#include "image.h"

class Filter
{
public:
	Filter() {};
	virtual ~Filter() {};
	virtual image::Image operator << (const image::Image & image) = 0;

};