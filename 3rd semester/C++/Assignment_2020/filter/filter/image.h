#ifndef _IMAGE
#define _IMAGE


#include "imageio.h"
#include "ppm.h"
#include "array2d.h"
#include "array2d.hpp"
#include "vec3.h"
#include <string>

#include <iostream>
using namespace std;



namespace  image
{

	class Image : public ImageIO, public math::Array2D<math::Vec3<float>>
	{
	public:

		Image() {};


		bool load(const std::string & filename, const std::string & format)
		{
			buffer.clear();
			const char* path = filename.c_str();
			const math::Vec3<float>* data_buffer;
			if (format != "ppm")
			{
				cerr << "Not a .ppm format";
				return false;
			}
			else
			{
				data_buffer = (math::Vec3<float>*)image::ReadPPM(path, (int*)&width, (int*)&height);

				setData(data_buffer);
				if (buffer.empty())
				{
					width = 0;
					height = 0;

					cerr << "Empty buffer";
					return false;
				}

				return true;
			}
		}


		bool save(const std::string & filename, const std::string & format)
		{
			if (format != "ppm")
			{
				cerr << "Not a .ppm format";
				return false;
			}

			const char* path = filename.c_str();
			return image::WritePPM((float*)getRawDataPtr(), width, height, path);
		}

		math::Vec3<float> getPixel(unsigned int x, unsigned int y) const
		{
			return buffer[x + y * width];
		}


		void  setPixel(unsigned int x, unsigned int y, math::Vec3<float> & data) {
			buffer[x + y * width] = data;
		}


		void setWidth(unsigned int width) {
			this->width = width;
		}

		void setHeight(unsigned int height) {
			this->height = height;
		}

		void clearBuffer() {
			buffer.clear();
			for (int i = 0; i < width * height; ++i) {
				buffer.push_back(math::Vec3<float>(0,0,0));
			}
		}
	};

}

#endif