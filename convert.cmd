pandoc  -S chapter6\chapter6.md -o chapter6.pdf --latex-engine=xelatex --template=template\pm-template.latex --variable mainfont="FandolHei"  

pandoc -t docx  -S chapter6\chapter6.md -o chapter6.docx  
