// PB1 | ABHISHEK WAHANE | B1 PANEL 2

//Assembler Pass 1

package ssc1;

import java.io.*;
import java.util.*;

class Operator
{
	String name;
	String cls;
	int opcode;
	Operator(String a, String c, int op)
	{
		this.name = a;
		this.cls = c;
		this.opcode = op;
	}
}

class Register
{
	String name;
	int no;
	Register(String a, int op)
	{
		this.name = a;
		this.no = op;
	}
}

class Condition
{
	String name;
	int no;
	Condition(String a, int op)
	{
		this.name = a;
		this.no = op;
	}
}

class Symbol
{
	String name;
	int addr;
	int length;
	Symbol(String a, int op, int len)
	{
		this.name = a;
		this.addr = op;
		this.length = len;
	}
}

public class assemblerPass1
{
	public static void main(String[] args) throws IOException
	{

		///Making the opp list
		ArrayList<Operator> aList = new ArrayList<>(25);

		aList.add(new Operator("STOP","IS",0));
		aList.add(new Operator("ADD","IS",1));
		aList.add(new Operator("SUB","IS",2));
		aList.add(new Operator("MULT","IS",3));
		aList.add(new Operator("MOVER","IS",4));
		aList.add(new Operator("MOVEM","IS",5));
		aList.add(new Operator("COMP","IS",6));
		aList.add(new Operator("BC","IS",7));
		aList.add(new Operator("DIV","IS",8));
		aList.add(new Operator("READ","IS",9));
		aList.add(new Operator("PRINT","IS",10));
		aList.add(new Operator("DC","DL",1));
		aList.add(new Operator("DS","DL",2));
		aList.add(new Operator("START","AD",1));
		aList.add(new Operator("END","AD",2));
		aList.add(new Operator("ORIGIN","AD",3));
		aList.add(new Operator("EQU","AD",4));
		aList.add(new Operator("LTORG","AD",5));

                System.out.println("---OP TAB---");
                for (Operator i : aList) {
                    System.out.print(i.name + "  ");
                    System.out.print(i.cls + "  ");
                    System.out.println(i.opcode);
                }

		///Making the opp list
		ArrayList<Register> rList = new ArrayList<Register>(25);
		rList.add(new Register("AREG",1));
		rList.add(new Register("BREG",2));
		rList.add(new Register("CREG",3));
		rList.add(new Register("DREG",4));

                System.out.println("\n---REG TAB--- ");

                for (Register i : rList) {
                    System.out.print(i.name + "  ");
                    System.out.println(i.no);
                }

		//Making the condition list
		ArrayList<Condition> cList = new ArrayList<Condition>(25);
		cList.add(new Condition("LT",1));
		cList.add(new Condition("LE",2));
		cList.add(new Condition("EQ",3));
		cList.add(new Condition("GT",4));
		cList.add(new Condition("GE",5));
		cList.add(new Condition("ANY",6));

                System.out.println("\n---COND TAB---");
                for (Condition i : cList) {
                    System.out.print(i.name + "  ");
                    System.out.println(i.no);
                }

		BufferedReader objReader = new BufferedReader(new FileReader("file.asm"));
		String strCurrentLine;

		//Making the symbol tables
		ArrayList<Symbol> symboltable = new ArrayList<Symbol>(25);

		//Creating the output files
		FileWriter fw = new FileWriter("Output.txt");
		BufferedWriter write = new BufferedWriter(fw);

		//Line number
		int line = 0;
		while ((strCurrentLine = objReader.readLine()) != null)
		{
		String[] splited = strCurrentLine.split("\\s+");

		if(!splited[0].equals("-"))
		{
			int found = 0;
			int index=0;
			Symbol x = null;
			Iterator<Symbol> sti = symboltable.iterator();
			while(sti.hasNext())
			{
				x = sti.next();
				if(x.name.equals(splited[0]))
				{
					found = 1;
					break;
				}
				index++;
			}
			if(found==0)
			{
				//Create a new entry
				if(splited[1].equals("DS"))
				{
					symboltable.add(new Symbol(splited[0],line,Integer.parseInt(splited[2])));
				}
				else
				{
					symboltable.add(new Symbol(splited[0],line,1));
				}
			}
			else
			{
				//Update the index
				x.addr = line;
				if(splited[1].equals("DS"))
				{
					x.length = Integer.parseInt(splited[2]);
				}
				else
				{
					x.length = 1;
				}

			}

		}

		//Incrementing the line count first
		if(splited[1].equals("START"))
		{
			write.write( "    (AD,1) (C,"+Integer.parseInt(splited[2])+")");
			write.newLine();
			line = Integer.parseInt(splited[2]);
		}
		else if(splited[1].equals("DS"))
		{
			write.write( Integer.toString(line)+") (DL,2) (C,"+Integer.parseInt(splited[2])+")");
			write.newLine();
			line = line + Integer.parseInt(splited[2]);
		}
		else
		{
			//checking in alist
			int found_ina = 0;
			Operator x = aList.get(0);
			Iterator<Operator> a_itr = aList.iterator();
			while(a_itr.hasNext())
			{
			 x = a_itr.next();
			 if(x.name.equals(splited[1]))
				{
				found_ina = 1;
				break;
				}
			}
			if(found_ina == 1)
			{
				if(x.cls.equals("AD"))
				{
					if(splited[2].equals("-"))
					{
						write.write(line+") (AD,"+x.opcode+")");
						write.newLine();
					}
				}
				if(x.cls.equals("IS"))
				{
					String ops = line+") (IS,"+x.opcode+")";
					String[] operands = splited[2].split(",");
					for(int i=0;i<2;i++)
					{
						String op = operands[i];
						int found_inreg = 0;
						Register y = rList.get(0);
						Iterator<Register> r_itr = rList.iterator();
						while(r_itr.hasNext())
						{
							y = r_itr.next();
							if(y.name.equals(op))
							{
								found_inreg = 1;
								break;
							}
						}
						if(found_inreg==1)
						{
							//If it is a register
							ops = ops+" ("+y.no+")";
						}
						else
						{
							int found_incond = 0;
							Condition z = cList.get(0);
							Iterator<Condition> c_itr = cList.iterator();
							while(c_itr.hasNext())
							{
								z = c_itr.next();
								if(z.name.equals(op))
								{
									found_incond = 1;
									break;
								}
							}
							if(found_incond==1)
							{
								//If it is a condition
								ops = ops+" ("+z.no+")";
							}

							else
							{
								//If it is a symbol
								Symbol s;
								Iterator<Symbol> sti = symboltable.iterator();
								int index = 1;
								int sym_found = 0;
								while(sti.hasNext())
								{
								s = sti.next();
								if(s.name.equals(op))
								{
									sym_found = 1;
									break;
								}
								index++;
								}
								if(sym_found==0)
								{
									symboltable.add(new Symbol(op,-1,-1));
								}
								ops = ops+" (S,"+(index)+")";
							}

						}

					}
					write.write(ops);
					write.newLine();
				}

				if(x.cls.equals("DL"))
				{
					int len = splited[2].length();
					write.write(line+") (DL,"+x.opcode+") (C,"+splited[2].substring(1,len-1)+")");
					write.newLine();
				}
			}
			line++;
		}

		}

    System.out.println("\n---SYM TAB---");
		Iterator<Symbol> display = symboltable.iterator();
		Symbol x;
		while(display.hasNext())
		{
			x = display.next();
			System.out.println(x.name + " " + x.addr + " " + x.length);
		}
		write.close();
	}
}

/*

ASM INPUT FILE:

- START 100
A DS 3
L1 MOVER AREG,B
- ADD AREG,C
- MOVEM AREG,D
D DC '2'
- ADD BREG,A
B DC '1'
C DC '3'
- END -

OUTPUT:

---OP TAB---
STOP  IS  0
ADD  IS  1
SUB  IS  2
MULT  IS  3
MOVER  IS  4
MOVEM  IS  5
COMP  IS  6
BC  IS  7
DIV  IS  8
READ  IS  9
PRINT  IS  10
DC  DL  1
DS  DL  2
START  AD  1
END  AD  2
ORIGIN  AD  3
EQU  AD  4
LTORG  AD  5

---REG TAB---
AREG  1
BREG  2
CREG  3
DREG  4

---COND TAB---
LT  1
LE  2
EQ  3
GT  4
GE  5
ANY  6

---SYM TAB---
A 100 3
L1 103 1
B 108 1
C 109 1
D 106 1

*/
