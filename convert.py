import sys
from htmldocx import HtmlToDocx
from docx import Document

html_file = 'assignment_report.html'
docx_file = 'assignment_report.docx'

new_parser = HtmlToDocx()

# Create a new document
doc = Document()

# Add HTML content
with open(html_file, 'r', encoding='utf-8') as f:
    html_content = f.read()

new_parser.add_html_to_document(html_content, doc)

# Save the document
doc.save(docx_file)
print("Conversion complete: " + docx_file)
