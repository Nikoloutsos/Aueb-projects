#include "Image.h"
#include "Vec3.h"
#include <vector>
#include <iostream>
using namespace math;

namespace imaging 
{
	
	Vec3<float> Image::getPixel(unsigned int x, unsigned int y) const {
		if (x >= 0 && x <= width && y >= 0 && y <= height) {
			Vec3<float> temp = buffer[y * width + x];
			return temp ;
		}
		return Vec3<float>(0, 0, 0);
	}

	void Image::setPixel(unsigned int x, unsigned int y, Vec3<float> & value) {
		if (x >= 0 && x <= width && y >= 0 && y <= height) {
			buffer[x + y * width] = value;
		}
	}



	Image::Image() {
	}

	Image::Image(unsigned int width, unsigned int height) : Array(width, height) {
		//initializing all image pixels with 0
		Vec3<float> vec(0, 0, 0);
		for (int i = 0; i < width*height; i++) {
			buffer.push_back(vec);
		}
	}


	Image::Image(unsigned int width, unsigned int height, const Vec3<float> * data_ptr) :Array(width, height) {
		//initializing all image pixels with 0
		 /*Vec3<float> vec(0, 0, 0);
		 for (int i = 0; i < width*height; i++) {
			 buffer.push_back(vec);
		 }*/
		
		setData(data_ptr);
	}


	Image::Image(const Image &src) :Array(src) {
		/*int w = src.getWidth();
		int h = src.getHeight();
		width = w;
		height = h;
		//Array(w,h);
		const Vec3<float> * temp;
		temp = src.buffer.data();
		setData(temp);*/
	}

	Image::~Image() {
		// 12/9/18
	}

	Image & Image::operator = (const Image & right) {
		int w = right.getWidth();
		int h = right.getHeight();
		Image(w, h);
		const Vec3<float> * temp = const_cast<const Vec3<float> *> (right.buffer.data());
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
				buffer.clear();
				for (int i = 0; i < width*height*3; i= i + 3) {
					buffer.push_back(Vec3<float>(*(p_f + i), *(p_f + i + 1), *(p_f + i + 2)));
				}
				delete[]p_width;
				delete[]p_height;
				delete[]p_f;
				return true;
			}
			else {
				delete[]p_width;
				delete[]p_height;
				return false;
			}

		}
		else {
			std::cout << "Not a ppm image. \n";
			return false;
		}

	}
	bool Image::save(const std::string & filename, const std::string & format) {
		if (buffer.empty()) return false;

		const char *p_path = filename.c_str();
		if (format == "ppm")
			return WritePPM((float *)buffer.data(), width, height, p_path);
		else
			return false;
	}

}//namespace imaging
