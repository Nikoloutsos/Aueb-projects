#include "ppm.h"
#include <string>
#include <fstream>
#include <iostream>

void printImageInfo(int width, int height) {
	std::cout << "Image dimensions are: " << width << "*" << height << "\n";
}

namespace imaging {
	float * ReadPPM(const char * filename, int * w, int * h) {
		int g;
		int vol = 0;
		std::string line;
		float * p_returnValue;
		int size_of_image;

		std::ifstream read_stream;;
		read_stream.open(filename, std::ios::in | std::ios::binary);
		read_stream >> line;
		if (read_stream.fail()) {
			std::cerr << "Problem with opening the file: " << filename << "\n";
			return nullptr;
		}

		if (line == "p6" || line == "P6") {
			read_stream >> *(w) >> *(h) >> vol;

			if (read_stream.fail()) {
				std::cerr << "Not accepted P6 header \n";
				read_stream.close();
				return nullptr;
			}



			if (*(w) < 0 || *(h) < 0 || (vol <= 0 || vol > 255)) {
				read_stream.close();//not right dimesions
				return nullptr;
			}

			printImageInfo(*(w), *(h));
			g = read_stream.tellg();
			read_stream.seekg(g + 1, 0);

			size_of_image = 3 * (*(w))* (*(h));

			p_returnValue = new float[size_of_image];
			char* temp = new char[size_of_image];
			read_stream.read(temp, size_of_image);
			unsigned char* temp_un = (unsigned char*)temp;

			for (int i = 0; i < size_of_image; i++) {
				*(p_returnValue + i) = (temp_un[i]) / 255.0f;
			}

			read_stream.close();

			delete[] temp;
			return p_returnValue;
		}
		else {
			std::cerr << "Not a P6 image  \n";
			read_stream.close();
			return nullptr;
		}
	}



	bool WritePPM(const float * data, int w, int h, const char * filename) { // 12/9/18
		std::ofstream write_stream;
		write_stream.open(filename, std::ios::binary | std::ios::out);
		if (write_stream.fail()) {
			std::cerr << "Write process failed \n";
			return false;
		}
		else {
			write_stream
				<< "P6 \n"
				<< w << "\n"
				<< h << "\n"
				<< "255\n";

			int size = 3 * h*w;
			char* p_rawData = new char[size];
			for (int i = 0; i < size; i++) {
				*(p_rawData + i) = (char)((*(data + i))*255.0f);
			}
			write_stream.write(p_rawData, size);
			write_stream.close();

			delete[]p_rawData;
			return true;
		}
	}
}