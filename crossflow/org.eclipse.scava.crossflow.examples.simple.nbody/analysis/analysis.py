# Import pandas
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

sns.set(style="ticks", color_codes=True)

# reading csv file
folder = "/Users/horacio/temp/stars/"
timing_df = pd.read_csv(folder + "100_timing.csv")
g = sns.catplot(x="bodies", y="total", hue="impl", jitter=True, data=timing_df, legend=False)
g.set(yscale="log")
plt.legend(loc='upper left')
# plt.show()
plt.savefig("total.pdf", bbox_inches='tight')

g = sns.catplot(x="bodies", y="MFLOP/s", hue="impl", jitter=True, data=timing_df, legend=False)
g.set(yscale="log")
plt.legend(loc='upper left')
# plt.show()
plt.savefig("gflops.pdf", bbox_inches='tight')

g = sns.catplot(x="bodies", y="global", hue="impl", jitter=True, data=timing_df, legend=False)
g.set(yscale="log")
plt.legend(loc='upper left')
# plt.show()
plt.savefig("global.pdf", bbox_inches='tight')

g = sns.catplot(x="bodies", y="GMFLOP/s", hue="impl", jitter=True, data=timing_df, legend=False)
g.set(yscale="log")
plt.legend(loc='upper left')
# plt.show()
plt.savefig("tgflops.pdf", bbox_inches='tight')


timing_df = pd.read_csv(folder + "100_timing2.csv")

g = sns.catplot(x="bodies", y="MFLOP/s", hue="impl", jitter=True, data=timing_df, legend=False)
g.set(yscale="log")
plt.legend(loc='upper left')
# plt.show()
plt.savefig("gflops2.pdf", bbox_inches='tight')

g = sns.catplot(x="bodies", y="global", hue="impl", jitter=True, data=timing_df, legend=False)
g.set(yscale="log")
plt.legend(loc='upper left')
# plt.show()
plt.savefig("global2.pdf", bbox_inches='tight')

g = sns.catplot(x="bodies", y="GMFLOP/s", hue="impl", jitter=True, data=timing_df, legend=False)
g.set(yscale="log")
plt.legend(loc='upper left')
# plt.show()
plt.savefig("tgflops2.pdf", bbox_inches='tight', legend=False)