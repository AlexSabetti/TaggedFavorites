/**
 * 
 */
let appliedTags = new Array();
let allTags = new Array();

 function tagClickedAdd(tagName){
	const delivery = {mediaTagList: appliedTags, tagName: tagName}
	fetch('/api/add', {method: 'POST', headers:{'Content-Type': 'application/json'},'body': JSON.stringify(delivery),}).then(response => response.json()).then(payload =>{
		if(payload != 0){
			
			appliedTags.push(tagName);
			let newAllTags = new Array();
			allTags.forEach(tag =>{
				if(tag != tagName){
					newAllTags.push(tag);
				}
			});
			allTags = newAllTags;
		}
	});
	startUpTags();
 }
 
 function tagClickedRemove(tagName){
	allTags.push(tagName);
	let newAppliedTags = new Array();
	appliedTags.forEach(tag =>{
		if(tag != tagName){
			newAppliedTags.push(tag);
			appliedTags.pop;
		}
	});
	appliedTags = newAppliedTags;
	startUpTags();
 }
 


function startUp(){
	const url = window.location.pathname.split('/');
	if(url.includes('edit')){
		fetch('/api/startup/edit', {method: 'GET', headers: {'Content-Type': 'application/json', 'mediaId': document.getElementById('mediaId').innerText}}).then(response => response.json()).then(payload => {
			appliedTags = payload[0];
			allTags = payload[1];
		});
	} else {
		fetch('/api/startup', {method: 'GET', headers: {'Content-Type': 'application/json', 'stateRequest' : 'stateData'}}).then(response => response.json()).then(payload => {
		console.log(payload)
		allTags = payload;
		startUpTags();
		}); 
	}
	
	
}

function startUpTags(){
	console.log(document.getElementById("taglist"))
	let tagList = document.getElementById('taglist')
	console.log(allTags);
	
	tagList.innerHTML = '';
	allTags.forEach(tagall => {const li = document.createElement('li');
	li.textContent = tagall;
	li.addEventListener('click', () => {
		tagClickedAdd(tagall)
	})
	tagList.appendChild(li);
	});
	
	let unTagList = document.getElementById('untaglist')
	unTagList.innerHTML='';
	appliedTags.forEach(tag => {const li = document.createElement('li');
	li.textContent = tag;
	li.addEventListener('click', () => {
		tagClickedRemove(tag)
	})
	unTagList.appendChild(li)
	});
	
	document.getElementById('hiddenList').value = Array.from(appliedTags).join(',');
	console.log("here")
	
}

function updateTags(){
	
}


startUp();