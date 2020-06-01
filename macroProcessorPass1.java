// PB1 | ABHISHEK WAHANE | B1 PANEL 2

//MacroProcessor Pass 1

package ssc3;

import java.io.*;

public class macroProcessorPass1
{
	public static void main(String[] args) throws IOException
	{
		BufferedWriter fw_mnt = new BufferedWriter(new FileWriter("MNT_table.txt"));
		fw_mnt.write("Index\tName\tMDT Index");
		fw_mnt.newLine();
		int mntp=0;

		BufferedWriter fw_mdt = new BufferedWriter(new FileWriter("MDT_table.txt"));
		fw_mdt.write("Index\tName");
		fw_mdt.newLine();
		int mdtp=0;

		BufferedWriter fw_ala = new BufferedWriter(new FileWriter("ALA_table.txt"));
		fw_ala.write("Index\tName");
		fw_ala.newLine();
		int alap=0;

		BufferedReader objReader = new BufferedReader(new FileReader("Input.txt"));
		String strCurrentLine;


		//Macro trigger
		int ismacro = 0;
		while ((strCurrentLine = objReader.readLine()) != null)
		{
			String[] splited = strCurrentLine.split("\\s+");
			if (splited[0].equals("MACRO"))
			{
				//The name is yet to be stored
				ismacro = 2;
				continue;
			}
			if (ismacro==2)
			{
				//Trigger to MDT status
				ismacro = 1;
				//Storing the name in MNT
				fw_mnt.write(mntp + "\t" + splited[0] + "\t" + mdtp);
				fw_mnt.newLine();
				mntp++;
				//Storing the variables in ALA
				int len = splited.length;
				String argument;
				for( int i =1; i<len;i++)
				{
					argument = splited[i];
					fw_ala.write(alap + "\t" + argument);
					fw_ala.newLine();
					alap++;
				}
			}

			if(ismacro == 1)
			{
				//Storing in MDT
				fw_mdt.write(mdtp + "\t" + strCurrentLine);
				fw_mdt.newLine();
				mdtp++;
			}

			if(splited[0].equals("MEND"))
			{
				ismacro = 0;
			}

		}

		fw_mnt.close();
		fw_mdt.close();
		fw_ala.close();

	}

}


/*

INPUT:

MACRO
DECR &ARG1 &ARG2
SUB AREG &ARG1
SUB BREG &ARG2
MEND
MACRO
ABHI &ARG3 &ARG4 &ARG5
SUB AREG &ARG3
ADD BREG &ARG4
MULT CREG &ARG5
MEND

START
MOVER AREG S1
MOVER BREG S1
MOVER CREG S1
DECR D1 D2
ABHI D1 D2 D3
S1 DC 5
D1 DC 2
D2 DC 3
D3 DC 4
END



OUTPUT:

MNT TABLE
Index	Name	MDT Index
0	ABHI	      4
1	DECR        0

MDT TABLE
Index	Name
0	DECR &ARG1 &ARG2
1	SUB AREG &ARG1
2	SUB BREG &ARG2
3	MEND
4	ABHI &ARG3 &ARG4 &ARG5
5	SUB AREG &ARG3
6	ADD BREG &ARG4
7	MULT CREG &ARG5
8	MEND

ALA TABLE
Index	Name
0	&ARG1
1	&ARG2
2	&ARG3
3	&ARG4
4	&ARG5

*/
