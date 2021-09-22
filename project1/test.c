#include <stdio.h>
#include <stdlib.h>

void fill(int arr[]);
void shiftRight(int arr[], int pos, int final);
int findSortedPos(int arr[], int val, int final);
void insertSortedPos(int arr[], int pos, int final);

int main()
{
  int arr[100];
  int arr2[] = {1,2,3,4,10,5,6,7,8,9};

  //printf("%d \n", findSortedPos(arr2, 11, 10));
  //shiftRight(arr2, 5, 9);
  for(int i = 0; i < 10; i++)
  {
    printf("%d ", arr2[i]);
  }
  printf("\n");
  
  insertSortedPos(arr2, 4, 9);
  
  for(int i = 0; i < 10; i++)
  {
    printf("%d ", arr2[i]);
  }
  /*
  fill(arr);
  
  for(int i = 0; i < 100; i++)
  {
    printf("%d ", arr[i]);
    if(i % 10 == 0 && arr[i] != 100)
    {
      printf("\n");
    }
  }
  */

  printf("\n");
  
  return 0;
}



void fill(int arr[])
{
  for (int i = 100; i >= 0; i--)
    {
      arr[100-i] = i;
    }
}

void shiftRight(int arr[], int pos, int final)
{
  for(int i = final - 1; i >= pos; i--)
  {
    arr[i+1] = arr[i];
  }
}

int findSortedPos(int arr[], int val, int final)
{
  int i;
  for(i = 0; i < final; i++)
  {
    if(arr[i] >= val)
    {
      break;
    }
  }
  return i;
}

void insertSortedPos(int arr[], int pos, int final)
{
  int v = arr[pos];
  printf("%d \n", v);
  int p = findSortedPos(arr, v, final);
  printf("%d \n", p);
  shiftRight(arr, p, final);
  arr[p] = v;
}
 
