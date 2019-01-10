#pragma once
#include <vector>
#include "Vec3.h"
using namespace math;
using namespace std;
namespace math {
	template <class T> class Array {

	protected:
		std::vector<T> buffer;
		unsigned int width;
		unsigned int height;
		
	public:
		
		Array() : width(0) , height(0){
		}

		Array(unsigned int w, unsigned int h): width(w), height(h) {
		}

		Array(const Array & src) : width(src.width) , height(src.height)  {
			const Vec3<float> * temp;
			temp = src.buffer.data();
			setData(temp); 
		
		}

		T & operator()(int i, int j) {
			return buffer.at(i + j * width);
		}

		T * getRawDataPtr() const {
			return buffer.data();
		}

		void setData(const Vec3<float> * & data_ptr) {
		     buffer.clear();
			for (int i = 0; i < width*height; i++) {
				Vec3<float> current = *(data_ptr + i);
				buffer.push_back(current);
			}
		}

	
	};
}