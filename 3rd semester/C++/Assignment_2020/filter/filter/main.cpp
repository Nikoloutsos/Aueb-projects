#include <iostream>
#include "image.h"
#include "filterGamma.h"
#include "filterLinear.h"
#include <vector>
#include <string>

using namespace image;
using namespace std;

int main(int argc, char* argv[])
{
	string filePath;
	string fileFormat;
	string ImageToSaveFilePath;
	string g;
	string linerHelp[6];
	string helperString;
	vector<Filter*> filters;

	
	if (argc >= 3) //suppose there may be -f and image dir
	{
		filePath = argv[argc - 1];
		fileFormat = filePath.substr(filePath.find_last_of(".") + 1);
		ImageToSaveFilePath = filePath.substr(0, filePath.find_last_of("\\")) + "\\filtered_" + filePath.substr(filePath.find_last_of("\\") + 1);;
		cout << ImageToSaveFilePath << "\n";
			if (fileFormat == "ppm")
			{
			}
			else 
			{
				cout << "not  ppm image given \n";
				system("pause");
				return 0;
			}
			

	}
	else {
		cout << "not enough argumemnts \n";
		system("pause");
		return 0;
	}

	helperString = argv[1];
	if (helperString != "-f") 
	{
		cout << "not -f as first argumemnts \n";
		system("pause");
		return 0;
	}

	
	Image image;
	if (image.load(filePath, fileFormat) == false) 
	{
		cout << "problem with file loading \n";
		system("pause");
		return 0;
	}



	int index = 2;

	while (index < argc - 1) 
	{
		helperString = argv[index];

		if (helperString == "gamma") 
		{
			if (index + 1 < argc - 1) 
			{
				g = argv[index +  1];
				char* gamma;
				strtod(g.c_str(), &gamma);
				if (*gamma == 0) 
				{
					//add filter
					filters.push_back(new FilterGamma(stof(argv[index + 1])));
					//cout << "good gamma \n";
				}
				else 
				{
					cout << "not right gamma arguments given";
					system("pause");
					return 0;
				}
			}
			else 
			{
				cout << "not enough space for gamma arguments \n";
				system("pause");
				return 0;
			}
			index += 2;
		}
		else if(helperString == "linear")
		{
			if (index + 6 < argc - 1)
			{
				for (int j = 1; j < 7; ++j) 
				{
					linerHelp[j -1 ] = argv[index + j];
				}
				
				
				for (int j = 0; j < 6; ++j) 
				{
					char* linear;
					strtod(linerHelp[j].c_str(), &linear);
					if (*linear == 0) 
					{
						continue;
					}
					else 
					{
						cout << "not right linear arguments given";
						system("pause");
						return 0;
					}
				}

				//filter;
				filters.push_back(new FilterLinear(stof(linerHelp[0]), stof(linerHelp[1]), stof(linerHelp[2]), stof(linerHelp[3]), stof(linerHelp[4]), stof(linerHelp[5]) ));
				//cout << "good linear";

			}
			else 
			{
				cout << "not enough space for linear arguments \n";
				system("pause");
				return 0;
			}
			index += 7;
		
		}
		else 
		{
			cout << "not a provided filter \n";
			system("pause");
			return 0;
		}
	}



	/*filters zone*/
	for (int i = 0; i < filters.size(); ++i) 
	{
		cout << "applying filter: " << i + 1 << "\n";
		image = *filters.at(i) << image;
	}



	if(image.save(ImageToSaveFilePath, fileFormat) == true)
	{
		cout << "THE END \n";
	}
	else 
	{
		cout << "problem with saving file \n";
	}
	

	system("pause");
	return 0;
}