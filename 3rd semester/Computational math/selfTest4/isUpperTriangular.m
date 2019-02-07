## Copyright (C) 2019 KwstasNiks
## 
## This program is free software: you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
## 
## This program is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
## 
## You should have received a copy of the GNU General Public License
## along with this program.  If not, see
## <https://www.gnu.org/licenses/>.

## -*- texinfo -*- 
## @deftypefn {} {@var{retval} =} isUpperTriangular (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: KwstasNiks <KwstasNiks@KWNSTANTINOS>
## Created: 2019-02-06

function retval = isUpperTriangular (A)
  for i = 1:size(A)(1,1)
    for j = 1:size(A)(1,2)
      if (i>j)
        if (A(i,j) != 0)
          retval = 0;
          return;
        endif
      endif  
  
    endfor  
  endfor  
  retval = 1;
  

endfunction
