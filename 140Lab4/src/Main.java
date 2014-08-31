import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class Main 
{
	public static void main (String[] args)
	{
		
		long[] mintermsArr = {1, 5, 14, 15, 16, 20, 22, 24, 25, 26, 29, 31, 32, 33, 41, 51, 56, 58, 63, 66, 76, 89, 95, 104, 105, 107, 108, 109, 117, 118, 123, 125, 127, 132, 138, 139, 146, 149, 151, 152, 156, 159, 160, 164, 165, 168, 169, 172, 173, 176, 178, 188, 197, 201, 203, 205, 206, 209, 211, 214, 215, 219, 223, 225, 236, 239, 240, 241, 245, 255, 259, 261, 263, 264, 267, 268, 272, 273, 280, 281, 286, 287, 289, 291, 295, 301, 311, 313, 320, 321, 325, 327, 330, 332, 337, 338, 340, 342, 343, 346, 347, 350, 352, 353, 356, 359, 362, 365, 370, 371, 377, 380, 381, 390, 391, 394, 397, 398, 401, 408, 419, 420, 427, 430, 437, 438, 439, 442, 444, 447, 449, 455, 457, 458, 463, 464, 467, 470, 473, 474, 475, 485, 493, 495, 496, 504, 507, 510, 514, 524, 534, 537, 542, 545, 546, 549, 550, 552, 557, 563, 566, 567, 571, 576, 581, 588, 593, 595, 597, 599, 600, 601, 602, 604, 612, 614, 626, 630, 641, 648, 650, 651, 653, 655, 656, 657, 659, 660, 663, 666, 667, 671, 675, 676, 678, 681, 684, 686, 688, 689, 690, 691, 693, 694, 697, 700, 701, 704, 706, 707, 715, 717, 720, 723, 727, 732, 735, 736, 738, 742, 747, 750, 751, 756, 757, 760, 761, 762, 764, 772, 778, 782, 783, 785, 786, 791, 792, 797, 800, 801, 804, 812, 814, 819, 821, 822, 824, 839, 840, 846, 848, 850, 853, 856, 862, 863, 866, 867, 868, 869, 870, 877, 880, 885, 887, 891, 899, 901, 902, 903, 904, 913, 914, 915, 918, 925, 926, 929, 931, 933, 934, 940, 941, 948, 950, 951, 953, 954, 955, 963, 966, 970, 974, 975, 976, 985, 992, 996, 999, 1001, 1002, 1005, 1006, 1008, 1009, 1010, 1016, 1018, 1023, 1024, 1025, 1026, 1041, 1042, 1043, 1044, 1046, 1048, 1049, 1051, 1055, 1056, 1057, 1061, 1062, 1063, 1064, 1069, 1070, 1071, 1074, 1078, 1081, 1098, 1104, 1105, 1107, 1109, 1110, 1115, 1116, 1117, 1122, 1125, 1129, 1133, 1139, 1141, 1142, 1148, 1156, 1157, 1160, 1168, 1169, 1175, 1178, 1180, 1186, 1187, 1188, 1189, 1192, 1193, 1196, 1202, 1203, 1204, 1210, 1211, 1213, 1217, 1218, 1219, 1223, 1229, 1231, 1238, 1240, 1241, 1244, 1252, 1254, 1260, 1261, 1262, 1263, 1269, 1270, 1275, 1284, 1294, 1295, 1297, 1303, 1307, 1308, 1310, 1311, 1323, 1327, 1332, 1333, 1341, 1344, 1345, 1353, 1356, 1357, 1361, 1365, 1368, 1372, 1375, 1386, 1390, 1392, 1393, 1399, 1401, 1404, 1406, 1407, 1409, 1415, 1419, 1420, 1421, 1424, 1425, 1426, 1428, 1429, 1430, 1432, 1434, 1435, 1440, 1442, 1447, 1448, 1456, 1462, 1463, 1469, 1472, 1474, 1477, 1478, 1481, 1484, 1485, 1489, 1490, 1495, 1498, 1508, 1509, 1510, 1512, 1513, 1516, 1520, 1521, 1525, 1534, 1546, 1547, 1548, 1549, 1551, 1552, 1554, 1559, 1561, 1564, 1566, 1570, 1575, 1581, 1582, 1586, 1591, 1592, 1595, 1597, 1600, 1602, 1605, 1606, 1610, 1613, 1614, 1617, 1622, 1624, 1626, 1628, 1632, 1636, 1640, 1646, 1650, 1652, 1654, 1656, 1658, 1659, 1660, 1666, 1667, 1676, 1684, 1696, 1697, 1699, 1700, 1701, 1704, 1714, 1715, 1726, 1730, 1731, 1732, 1733, 1735, 1738, 1744, 1746, 1747, 1749, 1750, 1754, 1755, 1762, 1764, 1766, 1769, 1771, 1772, 1775, 1777, 1778, 1779, 1784, 1785, 1786, 1790, 1797, 1799, 1801, 1802, 1803, 1804, 1809, 1812, 1814, 1815, 1816, 1818, 1822, 1826, 1828, 1829, 1830, 1831, 1835, 1839, 1840, 1841, 1843, 1845, 1849, 1850, 1851, 1856, 1858, 1864, 1868, 1871, 1874, 1876, 1878, 1881, 1887, 1888, 1892, 1898, 1899, 1900, 1901, 1904, 1905, 1909, 1912, 1913, 1919, 1923, 1927, 1938, 1943, 1954, 1959, 1962, 1965, 1979, 1980, 1987, 1989, 1997, 1999, 2007, 2008, 2010, 2012, 2016, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2032};
//		
//
		long[] dontcaresArr = {7, 8, 27, 101, 703, 1334, 1842};
//		long[] mintermsArr = {0, 2, 6, 8, 11, 12, 14, 15};
//		long[] mintermsArr = {2, 4, 8, 10, 12, 13, 15};
//		long[] dontcaresArr = { 0, 1};
//		long[] mintermsArr = {0,1,2,5,6,7};
//		long[] mintermsArr = {0, 1, 3, 6, 7, 9, 10, 12, 13, 14, 19, 29, 30, 32, 33, 35, 36, 38, 40, 41, 42, 43, 44, 45, 48, 49, 50, 54, 55, 57, 58, 59, 62};
//		long[] dontcaresArr = { 9,25};

		ArrayList<Long> al = new ArrayList<Long>();
		
		for (int i = 0; i<mintermsArr.length; i++)
			al.add(mintermsArr[i]);

		ArrayList<Long> dc_al = new ArrayList<Long>();

		for (int i = 0; i < dontcaresArr.length; i++)
			dc_al.add(dontcaresArr[i]);
		
		Calendar cal = Calendar.getInstance();
		long maxMinterm = al.get(al.size() - 1);
		long maxDontCare = dc_al.get(dc_al.size() -1);
		if (maxDontCare > maxMinterm)
			maxMinterm = maxDontCare;
		long numVarsLong = (Long.highestOneBit(maxMinterm) << 1) - 1;
		int numVars = Long.bitCount(numVarsLong); 
		System.out.println("Number of Variables: " + numVars);
		
		BooleanExpression boEx = new BooleanExpression(al, dc_al, numVars);
		boEx.genVerilog("Lab4Minterms");
		System.out.println("Number of Minterms: " + boEx.getImplicantList().size());

		boEx.doTabulationMethod();
		System.out.println("Number of Prime Implicants: " + boEx.getImplicantList().size());
		boEx.genVerilog("Lab4PrimeImplicants");
		
		boEx.doQuineMcCluskey();
		System.out.println("Number of Prime Implicants After QM: " + boEx.getImplicantList().size());
		boEx.genVerilog("Lab4QuineMcCluskey");
		
		boEx.doPetricksMethod();
		System.out.println("Number of Prime Implicants in Minimal Cover: " + boEx.getImplicantList().size());
		
		Calendar cal2 = Calendar.getInstance();
		long milliseconds = cal2.getTimeInMillis() - cal.getTimeInMillis();
		
		System.out.println("Completed in " + milliseconds + " milliseconds.");
		
		boEx.genVerilog("Lab4Minimized");
		
		System.out.print("Test Completed!");
		
	}
}
