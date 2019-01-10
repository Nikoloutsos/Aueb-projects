#pragma once
#include "Image.h"
#include "Vec3.h"
#include <iostream>
#include <vector>
#include <math.h>       /* pow */

using namespace imaging;


/*
Base class
*/
class Filter {

public:
	virtual Image operator << (const Image & image) = 0;

	//Default constructor
	Filter() {}

	//Copy constructor
	


};
//=============================================================================================================

class FilterGamma: public Filter {
protected:
	double gamma;



public:
	Image operator << (const Image & image) {
		int h = image.getHeight();
		int w = image.getWidth();
		Image return_image = Image(w, h);
		

		std::cout << "height is : " << h << "\n width is :" << w;


	
		for (int i = 0; i < h; i++) {

			for (int j = 0; j < w; j++) {
				Vec3<float> currentPixel = (image.getPixel(j, i));
				currentPixel.x = pow(currentPixel.x, gamma);
				currentPixel.y = pow(currentPixel.y, gamma);
				currentPixel.z = pow(currentPixel.z, gamma);
				return_image.setPixel(j, i, currentPixel); 
			}

		}
		return return_image;
	}

	//Constructor
	FilterGamma(double gamma) {
		this->gamma = gamma;
	}





};
//=============================================================================================================

class FilterLinear: public Filter {
protected:
	double a1,a2,a3,c1,c2,c3; /* a1-a3 --> vector that is multiplaed with every pixel.
							     c1-c3 --> vector that is added to every pixel.*/




public:
	Image operator << (const Image & image) {
		int h = image.getHeight();
		int w = image.getWidth();
		Image return_image = Image(w, h);
	
		Vec3<float> mulVector(a1, a2, a3);
		Vec3<float> addVector(c1, c2, c3);

		Vec3<float> currentPixel;
		

		for (unsigned int i = 0; i < h; i++) {
			for (unsigned int j = 0; j < w; j++) {
				Vec3<float> currentPixel = image.getPixel(j, i)*mulVector + addVector;
				currentPixel = currentPixel.clampToUpperBound(1);
				currentPixel = currentPixel.clampToLowerBound(0);
				return_image.setPixel(j, i, currentPixel);
			}
		}

		return return_image;
	}

	//Constructor
	FilterLinear(double a1, double a2, double a3, double c1, double c2, double c3) {
		this->a1 = a1;
		this->a2 = a2;
		this->a3 = a3;
		this->c1 = c1;
		this->c2 = c2;
		this->c3 = c3;
		
	}
};


//=============================================================================================================


class FilterBlur : public Filter {
public:

	int N;
	vector<float> box;
	FilterBlur(){}
	FilterBlur(unsigned int N) {
		this->N = N;
		int numOfItemsInBox = pow(N, 2);
		for (int i = 0; i < numOfItemsInBox; i++) {
			box.push_back(1.0/numOfItemsInBox);
		}
	}

	Image operator << (const Image & image) {
		int h = image.getHeight();
		int w = image.getWidth();
		Image return_image = Image(w, h);


		Vec3<float> bluredPixel;
		Vec3<float> temp;
		//For every pixel.
		int boundX;
		int boundY;
		for (int i = 0; i < h; i++) { //i --> row
			for (int j = 0; j < w; j++) { //j --> column
				bluredPixel = Vec3<float>(0, 0, 0);
			
				
				//For each pixel in the box NxN
				for (int m = -N / 2; m <= N / 2; m++) { //row
					for (int n = -N / 2; n <= N / 2; n++) { //column
						temp = Vec3<float>(0, 0, 0);
						boundX = boundIndex(j + m, 0, w - 1);
						boundY = boundIndex(i + n, 0, h - 1);
						temp += image.getPixel(boundX, boundY);
						temp *= box.at((m + N/2)*N + n + N/2);
						bluredPixel += temp;
					}
				}
				Vec3<float> blp(bluredPixel);
				return_image.setPixel(j, i, blp);
			}
		}
		return return_image;
	}

protected:
	//Makes sure than the number returned is between the interval [minValue, maxValue]
	int boundIndex(int x, int minValue, int maxValue) {
		if (x > maxValue) return maxValue;
		if (x < minValue) return minValue;
		return x;
	}

};

//=============================================================================================================

class FilterLaplace : public FilterBlur{
public:
	vector<int> laplaceBox;
	FilterLaplace() {
		laplaceBox.push_back(0);	
		laplaceBox.push_back(1);
		laplaceBox.push_back(0);

		laplaceBox.push_back(1);
		laplaceBox.push_back(-4);
		laplaceBox.push_back(1);

		laplaceBox.push_back(0);
		laplaceBox.push_back(1);
		laplaceBox.push_back(0);
	}

	Image operator << (const Image & image) {
		int h = image.getHeight();
		int w = image.getWidth();
		Image return_image = Image(w, h);



		
		Vec3<float> sum;
		//For every pixel.
		
		for (int i = 0; i < h; i++) { //i --> row
			for (int j = 0; j < w; j++) { //j --> column
				sum = Vec3<float>(0, 0, 0);


				//For each pixel in the box 3x3
				for (int m = -1; m <= 1; m++) { //row
					for (int n = -1; n <= 1; n++) { //column
						sum += image.getPixel(
							boundIndex(j + n, 0, w - 1),
							boundIndex(i + m, 0, h - 1))*laplaceBox.at((m + 1)*3 + n + 1);
						
					}
				}
				sum = sum.clampToUpperBound(1);
				sum = sum.clampToLowerBound(0);
				return_image.setPixel(j, i, sum);
				
				
			}
		}
		return return_image;
	}

protected:
	//Makes sure than the number returned is between the interval [minValue, maxValue]
	int boundIndex(int x, int minValue, int maxValue) {
		if (x > maxValue) return maxValue;
		if (x < minValue) return minValue;
		return x;
	}


};

