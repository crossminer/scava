# Set Working directory
setwd("/Users/ochoa/Documents/cwi/crossminer/code/maracas/maracas/src/org/maracas/pipeline/sonarqube/sourcemeter/data/")

# Read CSV file
deltaData <- read.csv(file="stats-delta.csv", header=TRUE, sep=",")

# Get vectors from data table
labels <- deltaData$change
freq <- deltaData$number

# Set PDF file
pdf("delta.pdf", width=8, height=5)
# Perpendicular labels
par(las=2)
# Increase Y-axis margin
par(mar=c(5,8,1,1))

# Paint horizontal bar plot
bp = barplot(
	freq,
	axes = TRUE,
	border = NA,
	horiz = TRUE,
	log="x", 
	names.arg = labels, 
	xlab = "Changes"
)
# Add grid to background
grid()
# Add black margin to plot area
box("plot", col="black") 

# Close PDF file
dev.off()