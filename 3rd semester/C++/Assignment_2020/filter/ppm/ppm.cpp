#include "ppm.h"
#include <string>
#include <fstream>
#include <iostream>
using namespace std;


namespace image
{

	float * ReadPPM(const char * filename, int * w, int * h)
	{
		float * imageData;
		char * tempImageData;
		int colorValue;

		ifstream fileStream;
		string currentLine;
		int currPositionInStream;

		fileStream.open(filename, ios::in | ios::binary);

		if (fileStream.fail())
		{
			fileStream.close();
			cerr << "File didn't open while reading";
			return nullptr;
		}

		fileStream >> currentLine;
		if (!(currentLine == "p6" || currentLine == "P6"))
		{
			fileStream.close();
			cerr << "Not appropriate header(not a p6 image)";
			return nullptr;
		}

		fileStream >> *w >> *h >> colorValue;
		if (*w < 0 || *h < 0 || colorValue <= 0 || colorValue>255)
		{
			fileStream.close();
			cerr << "Not right dimensions of image or colorValue";
			return nullptr;
		}

		currPositionInStream = fileStream.tellg();
		fileStream.seekg(currPositionInStream + 1, 0);

		tempImageData = new char[3 * (*w)*(*h)];
		imageData = new float[3 * (*w)*(*h)];
		fileStream.read(tempImageData, 3 * (*w)*(*h));

		for (int i = 0; i < 3 * (*w)*(*h); ++i)
		{
			imageData[i] = (unsigned char)tempImageData[i] / 255.0;
		}

		fileStream.close();
		return imageData;

	}


	bool WritePPM(const float * data, int w, int h, const char * filename)
	{
		unsigned char * tempImageData;
		char* tempImage;
		tempImageData = new unsigned char[3 * w*h];
		for (int i = 0; i < 3 * w*h; ++i)
		{
			tempImageData[i] = data[i] * 255;
		}
		tempImage = (char*)tempImageData;

		ofstream fileStream;
		fileStream.open(filename, ios::binary | ios::out);

		if (fileStream.fail())
		{

			fileStream.close();
			cerr << "File didn't open while writing";
			return false;
		}

		fileStream << "P6" << endl << w << endl << h << endl << "255" << endl;
		fileStream.write(tempImage, 3 * w*h);
		fileStream.close();

		return true;
	}

}