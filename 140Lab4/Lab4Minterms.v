module Lab4Minterms(
input a,
input b,
input c,
output out
);
	assign out = ((~a)&(~b)&(~c))|(a&(~b)&(~c))|((~a)&b&(~c))|(a&(~b)&c)|((~a)&b&c)|(a&b&c);
endmodule
