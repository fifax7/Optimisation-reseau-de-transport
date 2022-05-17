package model;

public class Instance {


	/** Nombre d'entrepôts */
	public int n;
	
	/** Nombre de produits */
	public int r;
	
	/** Nombre de clients */
	public int m;
	
	/** Nombre de hubs */
	public int k;
	
	/** Nombre de types de camions */
	public int l;

	/** alpha[e][p] = quantité entière de produit p dans l’entrepôt e */
	public int alpha[][];
	
	/** mu[h] = capacité du hub h */
	public int mu[];
	
	/** beta[c][p] = demande du client c en propduit p */
	public int beta[][];
	
	/** kappa[t] = capacité des camions de type t */
	public int kappa[]; 
	
	/** gamma[t] = coût des camions de type t */
	public int gamma[];
	
	
	/** eta[t] = nombre de camions de type t */
	public int eta[];

	/** succE[e] = successeurs de l'entrepot e */
	public int[][] succE;
	
	/** succH[h] = successeurs du hub h */
	public int[][] succH;

	
	
	@Override
	public String toString() {
		
		StringBuffer b = new StringBuffer();
		
		b.append( n + "\n" );
		b.append( r + "\n" );
		b.append( m + "\n" );
		b.append( k + "\n" );
		b.append( l + "\n" );
		
		for (int e=1;e<=n;e++) {
			for (int p=1;p<=r;p++)
				b.append( alpha[e][p] +" ");
			b.append( "\n" );
		}
		
		for (int h=1;h<=k;h++)
			b.append( mu[h] +" " );
		b.append( "\n" );
		
		
		for (int c=1;c<=m;c++) {
			for (int p=1;p<=r;p++)
				b.append( beta[c][p] +" " );
			b.append( "\n" );
		}
		
		for (int t=1;t<=l;t++)
			b.append( kappa[t] +" " );
		b.append( "\n" );
		
		
		for (int t=1;t<=l;t++)
			b.append( gamma[t] +" " );
		b.append( "\n" );
		
		for (int t=1;t<=l;t++)
			b.append( eta[t] + " " );
		b.append( "\n" );
		
		
		for (int e=1;e<=n;e++) {
			for (int w : succE[e])
				b.append( w + " " );
			b.append( "\n" );
		}
		
		for (int h=1;h<=k;h++) {
			for (int w : succH[h])
				b.append( w + " " );
			b.append( "\n" );
		}
		
		return b.toString();
	}
		
	public String toDat() {
		
		StringBuffer b = new StringBuffer();
		
		b.append( "n = " + n + ";\n" );
		b.append( "r = " + r + ";\n" );
		b.append( "m = " + m + ";\n" );
		b.append( "k = " + k + ";\n" );
		b.append( "l = " + l + ";\n" );
		
		b.append( "alpha = [\n" );
		for (int e=1;e<=n;e++) {
			b.append( "\t[");
			for (int p=1;p<r;p++)
				b.append( alpha[e][p] +", ");
			b.append( alpha[e][r]+"]" );
			if (e<n) 
				b.append( ",\n" );
			else 
				b.append( "\n" );
		}
		b.append( "];\n" );
		
		b.append( "mu = [" );
		for (int h=1;h<k;h++)
			b.append( mu[h] +", " );
		b.append( mu[k]+"];\n" );
		
		b.append( "beta = [\n" );
		for (int c=1;c<=m;c++) {
			b.append( "\t[");
			for (int p=1;p<r;p++)
				b.append( beta[c][p] +", " );
			b.append( beta[c][r] +"]" );
			if (c<m)
				b.append( ",\n" );
			else
				b.append( "\n" );
		}
		b.append( "];\n" );
		
		b.append( "kappa = [" );
		for (int t=1;t<l;t++)
			b.append( kappa[t] +", " );
		b.append( kappa[l]+"];\n" );
		
		b.append( "gamma = [" );
		for (int t=1;t<l;t++)
			b.append( gamma[t] +", " );
		b.append( gamma[l]+"];\n" );
		
		b.append( "eta = [" );
		for (int t=1;t<l;t++)
			b.append( eta[t] +", " );
		b.append( eta[l]+"];\n" );
		
		b.append( "U = {\n" );
		for (int e=1;e<=n;e++) {
			for (int w : succE[e])
				b.append( "\t<"+e+","+w+">,\n");
		}
		
		for (int h=1;h<k;h++) {
			for (int w : succH[h])
				b.append( "\t<"+(h+n)+","+w+">,\n" );
		}
		for (int w=0; w<succH[k].length-1; w++)
			b.append( "\t<"+(k+n)+","+succH[k][w]+">,\n" );
		b.append("\t<"+(k+n)+","+succH[k][succH[k].length-1]+">\n" );
		b.append("};\n");		
		return b.toString();
	}
	
	
	
}
