all: compile clean

compile:
	pdflatex -shell-escape rapport.tex
	pdflatex -shell-escape rapport.tex
	# bibtex rapport
	# pdflatex -shell-escape rapport.tex
	# pdflatex -shell-escape rapport.tex

clean:
	rm -rf *.aux *.log *.toc *.out *.lof *.pyg *.bbl *.blg

mrproper: clean
	rm -rf rapport.pdf
