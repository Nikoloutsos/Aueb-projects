#pragma once
#include "filter.h"
#include "vec3.h"

class FilterLinear : public Filter {
private:
	math::Vec3<float> a, c;
public:
	FilterLinear();
	FilterLinear(float ar, float ag, float ab, float cr, float cg, float cb);
	virtual ~FilterLinear() {};

	image::Image operator << (const image::Image &image) override;
};