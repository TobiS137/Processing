package rna.tweens;

public enum EasingStyles {

	LINEAR {
		@Override
		public float ease(EasingModes dir, float x) {
			return x;
		}
	},
	SINE {
		@Override
		public float ease(EasingModes dir, float x) {
			switch (dir) {
			case IN:
				return (float) (1 - Math.cos((x * Math.PI) / 2));
			case OUT:
				return (float) Math.sin((x * Math.PI) / 2);
			case IN_OUT:
				return (float) (-(Math.cos(Math.PI * x) - 1) / 2);
			default:
				return 0;
			}
		}
	},
	CUBIC {
		@Override
		public float ease(EasingModes dir, float x) {
			switch (dir) {
			case IN:
				return x * x * x;
			case OUT:
				return (float) (1 - Math.pow(1 - x, 3));
			case IN_OUT:
				return (float) ((x < 0.5) ? 4 * x * x * x : 1 - Math.pow(-2 * x + 2, 3) / 2);
			default:
				return 0;
			}
		}
	},
	QUINT {
		@Override
		public float ease(EasingModes dir, float x) {
			switch (dir) {
			case IN:
				return x * x * x * x * x;
			case OUT:
				return (float) (1 - Math.pow(1 - x, 5));
			case IN_OUT:
				return (float) ((x < 0.5) ? 16 * x * x * x * x * x : 1 - Math.pow(-2 * x + 2, 5) / 2);
			default:
				return 0;
			}
		}
	},
	CIRC {
		@Override
		public float ease(EasingModes dir, float x) {
			switch (dir) {
			case IN:
				return (float) (1 - Math.sqrt(1 - Math.pow(x, 2)));
			case OUT:
				return (float) Math.sqrt(1 - Math.pow(x - 1, 2));
			case IN_OUT:
				return (float) ((x < 0.5) ? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2
						: (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2);
			default:
				return 0;
			}
		}
	},
	ELASTIC {
		@Override
		public float ease(EasingModes dir, float x) {
			double c4;
			switch (dir) {
			case IN:
				c4 = (2 * Math.PI) / 3;

				return (float) ((x == 0) ? 0
						: (x == 1) ? 1 : -Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * c4));
			case OUT:
				c4 = (2 * Math.PI) / 3;

				return (float) ((x == 0) ? 0
						: (x == 1) ? 1 : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * c4) + 1);
			case IN_OUT:
				c4 = (2 * Math.PI) / 4.5;

				return (float) ((x == 0) ? 0
						: (x == 1) ? 1
								: (x < 0.5) ? -(Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * c4)) / 2
										: (Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * c4)) / 2 + 1);
			default:
				return 0;
			}
		}
	},
	QUAD {
		@Override
		public float ease(EasingModes dir, float x) {
			switch (dir) {
			case IN:
				return x * x;
			case OUT:
				return 1 - (1 - x) * (1 - x);
			case IN_OUT:
				return (float) ((x < 0.5) ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2);
			default:
				return 0;
			}
		}
	},
	QUART {
		@Override
		public float ease(EasingModes dir, float x) {
			switch (dir) {
			case IN:
				return x * x * x * x;
			case OUT:
				return (float) (1 - Math.pow(1 - x, 4));
			case IN_OUT:
				return (float) ((x < 0.5) ? 8 * x * x * x * x : 1 - Math.pow(-2 * x + 2, 4) / 2);
			default:
				return 0;
			}
		}
	},
	EXPO {
		@Override
		public float ease(EasingModes dir, float x) {
			switch (dir) {
			case IN:
				return (float) ((x == 0) ? 0 : Math.pow(2, 10 * x - 10));
			case OUT:
				return (float) ((x == 1) ? 1 : 1 - Math.pow(2, -10 * x));
			case IN_OUT:
				return (float) ((x == 0) ? 0
						: (x == 1) ? 1 : x < 0.5 ? Math.pow(2, 20 * x - 10) / 2 : (2 - Math.pow(2, -20 * x + 10)) / 2);
			default:
				return 0;
			}
		}
	},
	BACK {
		@Override
		public float ease(EasingModes dir, float x) {
			double c1;
			double c3;
			switch (dir) {
			case IN:
				c1 = 1.70158;
				c3 = c1 + 1;

				return (float) (c3 * x * x * x - c1 * x * x);
			case OUT:
				c1 = 1.70158;
				c3 = c1 + 1;

				return (float) (1 + c3 * Math.pow(x - 1, 3) + c1 * Math.pow(x - 1, 2));
			case IN_OUT:
				c1 = 1.70158;
				c3 = c1 * 1.525;

				return (float) ((x < 0.5) ? (Math.pow(2 * x, 2) * ((c3 + 1) * 2 * x - c3)) / 2
						: (Math.pow(2 * x - 2, 2) * ((c3 + 1) * (x * 2 - 2) + c3) + 2) / 2);
			default:
				return 0;
			}
		}
	},
	BOUNCE {
		@Override
		public float ease(EasingModes dir, float x) {
			switch (dir) {
			case IN:
				return 1 - ease(EasingModes.OUT, 1 - x);
			case OUT:
				double n1 = 7.5625;
				double d1 = 2.75;

				if (x < 1 / d1) {
					return (float) (n1 * x * x);
				} else if (x < 2 / d1) {
					return (float) (n1 * (x -= 1.5 / d1) * x + 0.75);
				} else if (x < 2.5 / d1) {
					return (float) (n1 * (x -= 2.25 / d1) * x + 0.9375);
				} else {
					return (float) (n1 * (x -= 2.625 / d1) * x + 0.984375);
				}
			case IN_OUT:
				return (x < 0.5) ? (1 - ease(EasingModes.OUT, 1 - 2 * x)) / 2
						: (1 + ease(EasingModes.OUT, 2 * x - 1)) / 2;
			default:
				return 0;
			}
		}
	};

	abstract float ease(EasingModes dir, float x);
}