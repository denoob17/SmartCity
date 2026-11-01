import csv
import os
from collections import defaultdict
import matplotlib.pyplot as plt

os.makedirs("results/plots", exist_ok=True)
data = defaultdict(list)
with open("results/tables/results.csv") as f:
    for row in csv.DictReader(f):
        data[row["algorithm"]].append(
            (int(row["V"]), int(row["E"]), float(row["avg_time_ms"]), int(row["operations"]))
        )

def plot(index, xlabel, name):
    plt.figure(figsize=(8, 5))
    for algo, rows in data.items():
        plt.plot([r[index] for r in rows], [r[2] for r in rows], marker="o", label=algo)
    plt.xscale("log")
    plt.yscale("log")
    plt.xlabel(xlabel)
    plt.ylabel("Average time (ms)")
    plt.legend()
    plt.grid(True, which="both", alpha=0.3)
    plt.tight_layout()
    plt.savefig("results/plots/" + name)
    plt.close()

plot(0, "Number of vertices V", "time_vs_vertices.png")
plot(1, "Number of edges E", "time_vs_edges.png")

plt.figure(figsize=(8, 5))
for algo in ("Kruskal", "Prim"):
    rows = data[algo]
    plt.plot([r[1] for r in rows], [r[3] for r in rows], marker="o", label=algo)
plt.xscale("log")
plt.yscale("log")
plt.xlabel("Number of edges E")
plt.ylabel("Operations")
plt.legend()
plt.grid(True, which="both", alpha=0.3)
plt.tight_layout()
plt.savefig("results/plots/ops_kruskal_vs_prim.png")
plt.close()