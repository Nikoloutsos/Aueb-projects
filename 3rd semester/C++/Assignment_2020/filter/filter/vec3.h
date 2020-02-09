//------------------------------------------------------------
//
// C++ course assignment code 
//
// G. Papaioannou, 2017
//
//

#ifndef _Vec3_
#define _Vec3_

#include <cstddef>

namespace math

{
	/*! Represents a triplet of values of the same type S.
	 *
	 *  The Vec3 class is used as a generic three-dimensional vector and thus it defines several
	 *  numerical operators that can be used on Vec3<S> and S data.
	 */
	template <typename S>
	class Vec3
	{
	private:
		static S zero;

	public:
		// data members

		//! The first coordinate of the vector
		union { S x, r; };

		//! The second coordinate of the vector
		union { S y, g; };

		//! The third coordinate of the vector
		union { S z, b; };

		// member functions

		/*! Clamps all coordinates so that they are at least val
		*  \return the modified vector.
		*/
		Vec3<S> clampToLowerBound(S val) const
		{
			Vec3<S> v;
			v.x = x < val ? val : x;
			v.y = y < val ? val : y;
			v.z = z < val ? val : z;
			return v;
		}

		/*! Clamps all coordinates so that they are at most val
		*  \return the modified vector.
		*/
		Vec3<S> clampToUpperBound(S val) const
		{
			Vec3<S> v;
			v.x = x > val ? val : x;
			v.y = y > val ? val : y;
			v.z = z > val ? val : z;
			return v;
		}

		/*! Data access operator.
		 *
		 *  \param index is the zero-based index to the elements of the vector. No bounds checking is performed for performance reasons.
		 *
		 *  \return the index-th element of the vector.
		 */
		S & operator [] (size_t index)
		{
			return *((S*)this + index);
		}

		/*! Vector addition.
		 *
		 * \param right is the right-hand vector operand of the addition.
		 *
		 * \return a new vector that is the component-wise sum of the current and the right vectors.
		 */
		Vec3<S> operator + (const Vec3<S> & right)
		{
			Vec3<S> left;
			left.x = x + right.x;
			left.y = y + right.y;
			left.z = z + right.z;
			return left;
		}

		/*! Vector subtraction.
		*
		* \param right is the right-hand vector operand of the subtraction
		*
		* \return a new vector that is the component-wise subtraction of the current and the right vectors.
		*/
		Vec3<S> operator - (const Vec3<S> & right)
		{
			Vec3<S> left;
			left.x = x - right.x;
			left.y = y - right.y;
			left.z = z - right.z;
			return left;
		}

		/*! Component-wise vector multiplication.
		*
		* \param right is the right-hand vector operand of the multiplication
		*
		* \return a new vector whose elements are the component-wise multiplied elements of the current and the right vectors.
		*/
		Vec3<S> operator * (const Vec3<S> & right)
		{
			Vec3<S> left;
			left.x = x * right.x;
			left.y = y * right.y;
			left.z = z * right.z;
			return left;
		}

		/*! Vector-scalar multiplication.
		*
		* \param right is the right-hand scalar operand of the multiplication
		*
		* \return a new vector whose elements are the elements of the current vector multiplied with the right operand.
		*/
		Vec3<S> operator * (S right)
		{
			Vec3<S> left;
			left.x = x * right;
			left.y = y * right;
			left.z = z * right;
			return left;
		}

		/*! Scalar division.
		*
		* No checks are made for zero divisor.
		*
		* \param right is the scalar divisor.
		*
		* \return a new vector whose elements are the elements of the current vector divided by the right operand.
		*/
		Vec3<S> operator / (S right)
		{
			Vec3<S> left;
			left.x = x / right;
			left.y = y / right;
			left.z = z / right;
			return left;
		}

		/*! Component-wise vector division.
		*
		* No checks are made for zero divisor elements.
		*
		* \param right is the vector divisor.
		*
		* \return a new vector whose elements are the elements of the current vector divided by the corresponding elements of the right operand.
		*/
		Vec3<S> operator / (const Vec3<S> & right)
		{
			Vec3<S> left;
			left.x = x / right.x;
			left.y = y / right.y;
			left.z = z / right.z;
			return left;
		}

		/*! Addition assignment.
		*
		* \param right is the vector to add to the current one.
		*
		* \return a reference to the current vector after the change.
		*/
		Vec3<S> & operator += (const Vec3<S> & right)
		{
			x += right.x;
			y += right.y;
			z += right.z;
			return *this;
		}

		/*! Subtraction assignment
		*
		* \param right is the vector to subtract from the current one.
		*
		* \return a reference to the current vector after the change.
		*/
		Vec3<S> & operator -= (const Vec3<S> & right)
		{
			x -= right.x;
			y -= right.y;
			z -= right.z;
			return *this;
		}

		/*! Division assignment using a vector divisor.
		*
		* \param right is the vector divisor.
		*
		* \return a reference to the current vector after the change.
		*/
		Vec3<S> & operator /= (const Vec3<S> & right)
		{
			x /= right.x;
			y /= right.y;
			z /= right.z;
			return *this;
		}

		/*! Multiplication assignment using a vector multiplier.
		*
		* \param right is the vector multiplier.
		*
		* \return a reference to the current vector after the change.
		*/
		Vec3<S> & operator *= (const Vec3<S> & right)
		{
			x *= right.x;
			y *= right.y;
			z *= right.z;
			return *this;
		}

		/*! Multiplication assignment using a scalar multiplier.
		*
		* \param right is the scalar multiplier.
		*
		* \return a reference to the current vector after the change.
		*/
		Vec3<S> & operator *= (S right)
		{
			x *= right;
			y *= right;
			z *= right;
			return *this;
		}

		/*! Division assignment using a scalar divisor.
		*
		* \param right is the scalar divisor.
		*
		* \return a reference to the current vector after the change.
		*/
		Vec3<S> & operator /= (S right)
		{
			x /= right;
			y /= right;
			z /= right;
			return *this;
		}

		// constructors

		/*! Constructor with three-element initialization.
		 *
		 * \param x is the value of th first element.
		 * \param y is the value of the second element.
		 * \param z is the value of the third element.
		 */
		Vec3<S>(S x, S y, S z) : x(x), y(y), z(z) {}

		/*! Constructor with single-element initialization.
		*
		* \param val is the value that is replicated to all elements of the vector.
		*/
		Vec3<S>(S val) : x(val), y(val), z(val) {}

		/*! Default constructor.
		 *
		 * Initializes all elements to their default numerical value.
		 */
		Vec3<S>() : x(), y(), z() {}

		/*! Copy constructor constructor.
		 */
		Vec3<S>(const Vec3<S> & right) : x(right.x), y(right.y), z(right.z) {}

		// asignment and equality


		/*! Copy assignment operator.
		*
		* \param right is the vector to copy.
		*
		* \return a reference to the current vector after the assignment.
		*/
		Vec3<S> & operator = (const Vec3<S> & right)
		{
			x = right.x;
			y = right.y;
			z = right.z;
			return *this;
		}

		/*! Equality operator
		*
		* \param right is the vector to compare the current with.
		*
		* \return true if the vectors are equal element by element, false otherwise.
		*/
		bool operator == (const Vec3<S> & right) const
		{
			return x == right.x && y == right.y && z == right.z;
		}

		/*! Inequality operator
		*
		* \param right is the vector to compare the current with.
		*
		* \return true if at least one element of the current vector differs from the right vector, false otherwise.
		*/
		bool operator != (const Vec3<S> & right) const
		{
			return x != right.x || y != right.y || z != right.z;
		}
	};

	/*! Scalar-vector multiplication.
	*
	* \param a is the left-hand-side scalar multiplier.
	* \param v is the right-hand-side vector multiplicand.
	*
	* \return a new vector whose elements are the elements of the vector v multiplied with the scalar a.
	*/
	template<typename S>
	Vec3<S> operator * (S a, Vec3<S> v)
	{
		return v * a;
	}

	/*! Scalar-vector multiplication with scalar promotion to typename S.
	*
	* \param a is the left-hand-side scalar multiplier.
	* \param v is the right-hand-side vector multiplicand.
	*
	* \return a new vector whose elements are the elements of the vector v multiplied with the scalar a.
	*/
	template<typename S>
	Vec3<S> operator * (int a, Vec3<S> v)
	{
		return v * S(a);
	}

	/*! Vector-scalar multiplication with scalar promotion to typename S.
	*
	* \param v is the left-hand-side vector multiplicand.
	* \param a is the right-hand-side scalar multiplier.
	*
	* \return a new vector whose elements are the elements of the vector v multiplied with the scalar a.
	*/
	template<typename S>
	Vec3<S> operator * (Vec3<S> v, int a)
	{
		return v * S(a);
	}

	/*! Vector division by a scalar, using scalar promotion to typename S.
	*
	* \param v is the vector divident.
	* \param a is the scalar divisor.
	*
	* \return a new vector whose elements are the elements of the vector v divided by the scalar a.
	*/
	template<typename S>
	Vec3<S> operator / (Vec3<S> v, int a)
	{
		return v / S(a);
	}


	template <typename S> S Vec3<S>::zero = S(0);

} // namespace math

#endif