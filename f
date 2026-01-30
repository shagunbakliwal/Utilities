awk -F',' '
NR==FNR {
  p[$1]; p[$2]; next
}
{
  for (k in p)
    if ($0 ~ k) { print; next }
}
' list.txt data.txt > matched.txt