#include <iostream>

using namespace std;

void input(int& n, int A[])
{
	cin >> n;
	for (int i = 0; i < n; i++)
		cin >> A[i];
}

void coutOdd(int n, int A[])
{
	int c = 0;
	for (int i = 0; i < n; i++)
	{
		if (A[i] % 2 != 0)
		{
			c++;
		}
	}
	cout << c;
}

int main()
{
	int A[100];
	int n;
	input(n, A);
	coutOdd(n, A);
	return 0;
}