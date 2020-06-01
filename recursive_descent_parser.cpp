// PB1 Abhishek Wahane
// Recursive Descent Parser

#include <iostream>
#include <string>

/*
E -> TE_
E_ -> +TE_
T -> FT_
T_ -> *FT_
F-> id | (E)
*/

void E();
void E_();
void T();
void T_();
void F();

using namespace std;
int pointer = 0;

string s;

int main(){
	cout<<"Enter input string : "<<endl;
	cin >> s;
	s.push_back('\0');
	E();
	return 0;
}

void E() {
	T();
	E_();
	if(s.at(pointer) == '\0') {
		cout << "String accepted" << endl;
	}
	else {
		cout << "String not accepted" << endl;
	}
}

void E_(){
	if(s.at(pointer) == '+') {
		pointer++;
		T();
		E_();
	}

}

void T(){
	F();
	T_();
}

void T_(){
	if(s.at(pointer) == '*'){
		pointer++;
		F();
		T_();
	}
}

void F(){
	if(s.at(pointer) == 'i' && s.at(pointer + 1) == 'd' ){
		pointer++;
		pointer++;
	}
	else if (s.at(pointer) == '('){
		pointer++;
		E();
		if(s.at(pointer) == ')'){
			pointer++;
		}
	}
}


/*

OUTPUT:

(base) abhi@Abhisheks-MacBook-Pro ssc6 % g++ rdp.cpp
(base) abhi@Abhisheks-MacBook-Pro ssc6 % ./a.out
Enter input string :
id+id
String accepted
(base) abhi@Abhisheks-MacBook-Pro ssc6 % ./a.out
Enter input string :
id+id+id
String accepted
(base) abhi@Abhisheks-MacBook-Pro ssc6 % ./a.out
Enter input string :
id+id+E
String not accepted
(base) abhi@Abhisheks-MacBook-Pro ssc6 %

*/
