#include "filterLinear.h"
#include <iostream>

using namespace math;

FilterLinear::FilterLinear() : a(), c() {}

FilterLinear::FilterLinear(float ar, float ag, float ab, float cr, float cg, float cb) : a(ar, ag, ab), c(cr, cg, cb){
}

image::Image FilterLinear::operator<<(const image::Image & image) {
	unsigned int w = image.getWidth();
	unsigned int h = image.getHeight();

	image::Image filteredImage;
	filteredImage.setWidth(w);
	filteredImage.setHeight(h);
	filteredImage.clearBuffer();


	for (unsigned int i = 0; i < h; i++) {
		for (unsigned int j = 0; j < w; j++) {
			Vec3<float> pixelToChange = image.getPixel(j, i)*a + c;
			pixelToChange = pixelToChange.clampToLowerBound(0);
			pixelToChange = pixelToChange.clampToUpperBound(1);
			filteredImage.setPixel(j, i, pixelToChange);
		}
	}

	return filteredImage;

	}