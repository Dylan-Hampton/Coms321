#include <stdio.h>
#include <stdlib.h>

void fill(int arr[]);
void shiftRight(int arr[], int pos, int final);
int findSortedPos(int arr[], int val, int final);
void insertSortedPos(int arr[], int pos, int final);
void insertionSort(int arr[], int len);

int main()
{
  int arr[100];
  int arr2[] = {5,8,2,1,6,10,9,3};

  fill(arr);
  
  printf("Before: ");
  for(int i = 0; i < 100; i++)
  {
    printf("%d ", arr[i]);
  }
  printf("\n");

  insertionSort(arr, 100);

  //printf("%d \n", findSortedPos(arr2, 11, 10));
  //shiftRight(arr2, 5, 9);
  printf("After: ");
  for(int i = 0; i < 100; i++)
  {
    printf("%d ", arr[i]);
  }
  printf("\n");
  /*
  insertSortedPos(arr2, 2, 9);
  
  for(int i = 0; i < 8; i++)
  {
    printf("%d ", arr2[i]);
  }
  */
  
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
  for(i = 0; i <= final; i++)
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
  //printf("%d \n", v);
  int p = findSortedPos(arr, v, final);
  //printf("%d \n", p);
  shiftRight(arr, p, pos);
  arr[p] = v;
}

void insertionSort(int arr[], int len)
{
  for(int i = 1; i < len; i++)
  {
    insertSortedPos(arr, i, len - 1);
  }
}
