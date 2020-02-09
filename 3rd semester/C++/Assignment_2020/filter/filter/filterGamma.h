#pragma once
#include "filter.h"

class FilterGamma : public Filter {
private:
	float g;
public:
	FilterGamma();
	FilterGamma(float g);
	virtual  ~FilterGamma() {};

	image::Image operator << (const image::Image &image) override;
};