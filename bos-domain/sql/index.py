#!/usr/bin/env python3
# -*- coding: utf-8 -*-

'''
脚本功能：	在目标目录及其子目录下搜索所有的*.html文件，
			并生成链接保存在目标目录下的index.html中。
开发初衷：	在easyui的demo目录下生成index.html，便于学习时查阅
使用方法：  python index.py			//默认目标目录为脚本所在目录
			python index.py .		//指定目标目录为脚本所在目录
			python index.py #目录	//指定目标目录
'''
from sys import argv
import os
import sys

root="."
if(len(argv)>1):
	root=argv[1]
	if(not os.path.exists(root)):
		print("Error: "+root+"路径有误")
		sys.exit()

sections=[]
		
for dir, subdirs, files in os.walk(root, topdown=True):
	pages=[]
	for file in files:
		if(file.endswith('html')):
			p=os.path.join(dir, file).replace(root, '.')
			pages.append("<li><a href='"+ p +"'>" + p + "</a></li>")
	sections.append(pages)

with open(os.path.join(root, 'index.html'),'w') as f:
	f.write('<html lang="zh-cn"><head><meta http-equiv="content-type" content="text/html; charset=gbk"></head>\n')
	f.write('<body>\n')
	for section in sections:
		if(len(section)==0):
			continue
		f.write('\n<div style="border:1px #999 solid">\n\t')
		f.write('<ul>')
		f.write('<br/>\n\t'.join(section))
		f.write('\n</ul>\n</div>')
	f.write('</body>\n')
	f.write('</html>')

print("生成目录完毕")