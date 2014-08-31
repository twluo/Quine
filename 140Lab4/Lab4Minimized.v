module Lab4Minimized(
input a,
input b,
input c,
input d,
output out
);
	assign out = ((~a)&b&(~c))|((~a)&(~b)&c)|(a&c&d)|((~a)&(~c)&d);
endmodule
