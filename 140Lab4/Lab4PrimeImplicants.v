module Lab4PrimeImplicants(
input a,
input b,
input c,
output out
);
	assign out = ((~b)&(~c))|((~a)&(~c))|(a&(~b))|((~a)&b)|(a&c)|(b&c);
endmodule
