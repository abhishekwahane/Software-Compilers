%{
%}

%%
"int"|"float"|"char"|"double"|"byte"|"short"|"long"|"String" {printf("%s -> Data Type detected\n",yytext);}
"/*"	{ int flag=0;
		while(flag==0)
		{
		char c = input();
		if(c=='*'){
			char d;
			if((d=input())=='/'){
				flag=1;
				break;
				}
			else
				unput(d);
		  }
		else if(c=='\n'){
			continue;
		  }
		else{
			printf("%c",c);
		  }
	   }
	printf(" -> Multi Line Comment\n");
 }
"import" { char c;
		printf("%s",yytext);
		while((c=input())!='\n'){
			printf("%c",c);	continue;
		}
	printf(" -> Imported Library detected\n");
	}
"public"|"private"|"protected" {printf("%s -> Access Modifier detected\n",yytext);}
"+"|"-"|"*"|"/"|"=" {printf("%s ->Operator detected\n",yytext);}
";"|"," {printf("%s ->Seperator detected\n",yytext);}
"{" {printf("%s -> Opening Brackets\n",yytext);}
"}" {printf("%s -> Closing Brackets\n",yytext);}
"("  {printf("%s -> Opening Parentheses\n",yytext);}
")"  {printf("%s -> Closing Parentheses\n",yytext);}
"class"|"void"|"static" {printf("%s -> Keyword\n",yytext);}
" "|"\n" {continue;}
"//" { char c;
	printf("%s",yytext);
	while((c=input())!='\n'){
		printf("%c",c);
		continue;
	}
	printf(" -> Single Line Comment detected\n");
      }
[0-9]+ {printf("%s ->Constant detected\n",yytext);}
[a-zA-Z]+|"args"|"main" {printf("%s -> Identifiers detected\n",yytext);}
%%

int yywrap(){
	return 1;
}

int main(){
	extern FILE *yyin,*yyout;
	yyin=fopen("javaprog.java","r");
	yylex();
	return 0;
}
