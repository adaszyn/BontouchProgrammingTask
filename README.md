# Bontouch Thesis Programming task

## How the application works

1. Application fetches text file from `http://runeberg.org/words/ss100.txt`
2. Text file is parsed and put through adapter to RecyclerView
3. Applications __listens__ to text input widget change and updates __searchPhrase__ appropriately
4. RecyclerView refreshes to display elements which contents contain sub-string of the __searchPhrase__

## Additional libraries
1. Volley (https://developer.android.com/training/volley/)