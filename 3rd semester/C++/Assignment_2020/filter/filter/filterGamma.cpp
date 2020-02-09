#include "filterGamma.h"
#include "vec3.h"
#include <iostream>
#include <math.h>

FilterGamma::FilterGamma() : g(1) {};

FilterGamma::FilterGamma(float g) : g(g) {};

image::Image FilterGamma::operator<<(const image::Image & image) {
	unsigned int w = image.getWidth();
	unsigned int h = image.getHeight();

	image::Image filteredImage;
	filteredImage.setWidth(w);
	filteredImage.setHeight(h);
	filteredImage.clearBuffer();

	for (int i = 0; i < w; ++i) 
	{
		for(int j = 0; j < h; ++j)
		{
			math::Vec3<float> pixelToChange = image.getPixel(i, j);
			pixelToChange.x = pow(pixelToChange.x, g);
			pixelToChange.y = pow(pixelToChange.y, g);
			pixelToChange.z = pow(pixelToChange.z, g);
			filteredImage.setPixel(i, j, pixelToChange);
		}

	}

	return filteredImage;

}
