#include <iostream>
#include <vector>
#include <string>
#include "Image.h"
#include "ppm.h"
#include "Filter.h"

#include <stdexcept>


using namespace std;
using namespace imaging;

void wrong_syntax_exit_msg();

bool is_number(string s);

bool is_number_linear(string s1, string s2, string s3, string s4, string s5, string s6);

int main(int argc, char *argv[]) {
	//	check if image is ppm
	string current;
	string path;
	if (argc >= 2) {//it means that probably there is an image direction in the args
		path = argv[argc - 1];
		string format = path.substr(path.find_last_of(".") + 1);
		if (format != "ppm") {
			cout << "not a ppm image \nend of program \n";
			system("pause");
			return 0;
		}
	}
	else {//only the name of the exe is given in argv
		wrong_syntax_exit_msg();
	}




	//all the below are executed if image is ppm  
	//load image 
	Image image;
	if (image.load(path, "ppm") == false) {
		cout << "problem loading file \nEnd of program \n";
		system("pause");
		return 0;
	}
	//load was successful
	vector<Filter *> listOfFilters;

	for (int i = 1; i <= argc - 2; i++)
	{
		current = argv[i];
		if (current.compare("-f") == 0)
		{
			current = argv[i + 1];
			if ((argc - 1) >= (i + 3) && (current.compare("gamma") == 0)) //there is space for gamma format
			{
				if (is_number(argv[i + 2]))
				{
					current = argv[i + 3];
					if ((current.compare("-f") != 0) && (current.compare(path) != 0))
					{
						wrong_syntax_exit_msg();
					}
					double gamma = stod(argv[i + 2]);
					FilterGamma * gammaFilter = new FilterGamma(gamma);
					listOfFilters.push_back(gammaFilter);
					i = i + 2;
				}
				else
				{//after gamma there is not a double
					cout << "not double \n";
					wrong_syntax_exit_msg();
				}
			}
			else if ((argc - 1) >= (i + 8) && (current.compare("linear") == 0))
			{
				if (is_number_linear(argv[i + 2], argv[i + 3], argv[i + 4], argv[i + 5], argv[i + 6], argv[i + 7]))
				{
					current = argv[i + 8];
					if ((current.compare("-f") != 0) && (current.compare(path) != 0))
					{
						wrong_syntax_exit_msg();
					}
					double linearValues[6];
					for (int j = 0; j < 6; j++)
					{
						linearValues[j] = stod(argv[i + j + 2]);//string to double
					}
					Filter * linearFilter = new FilterLinear(linearValues[0], linearValues[1], linearValues[2], linearValues[3], linearValues[4], linearValues[5]);
					listOfFilters.push_back(linearFilter);
					i = i + 7;
				}
				else
				{//after linear there are not six doubles
					wrong_syntax_exit_msg();
				}
			}
			else if ((argc - 1) >= (i + 2) && (current.compare("laplace") == 0))
			{
				current = argv[i + 2];
				if ((current.compare("-f") != 0) && (current.compare(path) != 0))
				{
					wrong_syntax_exit_msg();
				}
				FilterLaplace * laplaceFilter = new FilterLaplace();
				listOfFilters.push_back(laplaceFilter);
				i = i + 1;
		    }
			else if ((argc - 1) >= (i + 3) && (current.compare("blur") == 0)) {
                if (is_number(argv[i + 2]))
				{
					current = argv[i + 3];
					if ((current.compare("-f") != 0) && (current.compare(path) != 0))
					{
						wrong_syntax_exit_msg();
					}
					double blur = stod(argv[i + 2]);
					FilterBlur * blurFilter = new FilterBlur(blur);
					listOfFilters.push_back(blurFilter);
					i = i + 2;
				}
				else
				{//after blur there is not a double
					cout << "not double \n";
					wrong_syntax_exit_msg();
				}

			}
			else
			{//syntax not appropriate
				wrong_syntax_exit_msg();
			}
		}
	}

	//Apply filters
	for (int i = 0; i < listOfFilters.size(); i++) {
		cout << "Applying filter: " << (i + 1) << "\n";
		image = *listOfFilters.at(i) << image;

	}


	//Save newImage.
	string saveAddress = path.substr(0, path.find_last_of(".")) + "_new.ppm";
	if (image.save(saveAddress, "ppm") == false) {
		cout << "Image not saved correctly \nEnd of program \n";
		system("pause");
		return 0;
	}

	cout << "Image saved \nEnd of program \n";
	system("pause");
	return 0;

}


void wrong_syntax_exit_msg() {
	cout << "This is not appropriate syntax of arguments \n";
	cout << "End of program \n";
	system("pause");
	exit(0);
}


bool is_number(string s) {
	char* p;
	strtod(s.c_str(), &p);
	return *p == 0; //returns true if *p==0
}

bool is_number_linear(string s1, string s2, string s3, string s4, string s5, string s6) {
	string s[] = { s1, s2, s3, s4, s5, s6 };
	char* p;
	for (int i = 0; i <= 5; i++) {
		strtod(s[i].c_str(), &p);
		if (*p != 0) return false;
	}
	return true;
}