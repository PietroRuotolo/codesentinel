import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('logdata.csv')
df_sorted = df.sort_values("count", ascending=False).head(10)
plt.bar(
    df_sorted['exception_type'], 
    df_sorted['count'])
plt.xticks(rotation=45, ha='right')
plt.tight_layout()
plt.xlabel("Exception")
plt.ylabel("Exceptions Count")
plt.title("Exception per Count")
plt.show()