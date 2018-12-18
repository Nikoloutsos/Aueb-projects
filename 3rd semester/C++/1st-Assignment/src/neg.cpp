#include <iostream>
#include <string>
#include "ppm.cpp" 
#include "Image.h"

void printAueb();
void loadAndSaveToNegative(std::string  &path, std::string &format);

int main(int argc, char *argv[]) {
	printAueb();
	std::string format;
	if (argc == 1) {
		std::cout << "Insert the image path: " << "\n";
		std::string path;
		std::getline(std::cin, path);
		format = path.substr(path.find_last_of(".") + 1);
		loadAndSaveToNegative(path, format);


	}
	else if (argc == 2) {
		std::string path = argv[1];
		format = path.substr(path.find_last_of(".") + 1);
		loadAndSaveToNegative(path, format);

	}
	else {
		std::cerr << "I cannot support this syntax :( \n";
	}

	system("pause");
	return 0;
}

void loadAndSaveToNegative(std::string &path, std::string &format) {
	imaging::Image image;
	bool flag = image.load(path, format);
	if (flag == true) {
		std::cout << "Image loaded successfully :) \n";
		
		std::string saveAddress = path.substr(0, path.find_last_of(".")) +"_neg.ppm";
        flag = image.save(saveAddress, "ppm");
		if (flag == true) {
			std::cout << "Image saved successfully :) \n";
		}
		else {
			std::cerr << "Error with saving the image :( \n";
		}
	}
	else {
		std::cerr << "Problem with loading file :( \n";
	}
}


void printAueb() {
	std::cout << R"(          _    _ ______ ____    _____  _____   ____       _ ______ _____ _______    _____)";
	std::cout << "\n";
	std::cout << R"(     /\  | |  | |  ____|  _ \  |  __ \|  __ \ / __ \     | |  ____/ ____|__   __|  / ____|_     _  )";
	std::cout << "\n";
	std::cout << R"(    /  \ | |  | | |__  | |_) | | |__) | |__) | |  | |    | | |__ | |       | |    | |   _| |_ _| |_)";
	std::cout << "\n";
	std::cout << R"(   / /\ \| |  | |  __| |  _ <  |  ___/|  _  /| |  | |_   | |  __|| |       | |    | |  |_   _|_   _|)";
	std::cout << "\n";
	std::cout << R"(  / ____ \ |__| | |____| |_) | | |    | | \ \| |__| | |__| | |___| |____   | |    | |____|_|   |_|  )";
	std::cout << "\n";
	std::cout << R"( /_/    \_\____/|______|____/  |_|    |_|  \_\\____/ \____/|______\_____|  |_|     \_____| )";
	std::cout << "\n";

}
