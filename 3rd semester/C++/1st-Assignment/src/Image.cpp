#include "Image.h"
#include <iostream>

namespace imaging
{


	Color * Image::getRawDataPtr() {
		return buffer;
	}


	Color Image::getPixel(unsigned int x, unsigned int y) const {
		if (x >= 0 && x <= width && y >= 0 && y <= height) {
			return *(buffer + y * width + x);
		}
		return Color(0, 0, 0);

	}


	void Image::setPixel(unsigned int x, unsigned int y, Color & value) {
		if (x >= 0 && x <= width && y >= 0 && y <= height) {
			*(buffer + x + y * width) = value;
		}
	}


	void Image::setData(const Color * & data_ptr) {
		unsigned char* temp = (unsigned char*)data_ptr;
		unsigned char r;
		unsigned char g;
		unsigned char b;
		for (int i = 3; i < 3 * width*height; i += 3) {
			r = *(temp + i - 2);
			g = *(temp + i - 1);
			b = *(temp + i);
			*(buffer + i / 3 - 1) = Color(r, g, b);
		}
	}

	Image::Image() {
		buffer = nullptr;
		width = 0;
		height = 0;
	}

	Image::Image(unsigned int width, unsigned int height) {
		this->width = width;
		this->height = height;
		buffer = new Color[width*height]; //Initialization of buffer
	}


	Image::Image(unsigned int width, unsigned int height, const Color * data_ptr) {
		Image(width, height);
		setData(data_ptr);
	}


	Image::Image(const Image &src) {
		Image(src.getWidth(), src.getHeight());

		const Color * temp;
		temp = src.buffer;
		setData(temp);

	}

	Image::~Image() {
		delete[] buffer;
	}

	Image & Image::operator = (const Image & right) {
		Image(right.getWidth(), right.getHeight());
		const Color * temp = const_cast<const Color *> (right.buffer);
		setData(temp);
		return *this;
	}


	bool Image::load(const std::string & filename, const std::string & format) {
		if (format == "ppm") {
			int *p_width = new int[1];
			int *p_height = new int[1];
			const char *p_path = filename.c_str();

			float * p_f = imaging::ReadPPM(p_path, p_width, p_height);

			if (p_f != nullptr) {
				width = *(p_width);
				height = *(p_height);
				buffer = (Color *)p_f;
				delete[]p_width;
				delete[]p_height;
				return true;
			}
			else {
				delete[]p_width;
				delete[]p_height;
				return false;
			}

		}
		else {
			std::cerr << "Not a ppm image. \n";
			return false;
		}

	}

	bool Image::save(const std::string & filename, const std::string & format) {
		if (buffer == nullptr) return false;

		const char *p_path = filename.c_str();
		if (format == "ppm")
			return WritePPM((float*)buffer, width, height, p_path);
		else
			return false;
	}



}//namespace imaging
