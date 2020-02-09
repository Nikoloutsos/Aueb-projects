#include "array2d.h"

	template <typename T>
	const unsigned int math::Array2D<T>::getWidth() const
	{
		return width;
	}


	template <typename T>
	const unsigned int math::Array2D<T>::getHeight() const
	{
		return height;
	}


	template <typename T>
	T * math::Array2D<T>::getRawDataPtr()
	{
		return buffer.data();
	}


	template <typename T>
	void math::Array2D<T>::setData(const T * const & data_ptr)
	{
		buffer.assign(data_ptr, data_ptr + width * height);
	}


	template <typename T>
	T & math::Array2D<T>::operator () (unsigned int x, unsigned int y)
	{
		return buffer[x + y * width];
	}


	template <typename T>
	math::Array2D<T>::Array2D(unsigned int width, unsigned int height, const T * data_ptr) : width(width), height(height)
	{
		setData(data_ptr);
	}



	template <typename T>
	math::Array2D<T>::Array2D(const Array2D &src) {
		width = src.width;
		height = src.height;
		buffer.clear();
		for (int i = 0; i < src.buffer.size(); ++i)
			buffer.push_back(src.buffer[i]);
	}


	template <typename T>
	math::Array2D<T>::~Array2D()
	{

	}

	template <typename T>
	math::Array2D<T> & math::Array2D<T>::operator = (const Array2D<T> & right)
	{
		width = right.width;
		height = right.height;
		buffer.clear();
		for (int i = 0; i < right.buffer.size(); ++i)
			buffer.push_back(right.buffer[i]);


		return *this;
	}

