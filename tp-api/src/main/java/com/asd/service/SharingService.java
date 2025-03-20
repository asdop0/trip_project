package com.asd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.asd.DTO.SharingDto;
import com.asd.exception.ResourceNotFoundException;
import com.asd.model.User;
import com.asd.repository.SharingRepository;
import com.asd.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SharingService {
	private final SharingRepository sharingRepository;
	private final UserRepository userRepository;
	
	// 자신의 일정 공유
	public void sharingMyTrip(String userId, String receiver, String trip, String auth) {
        String receiverId = userRepository.getIdByNickname(receiver);

		User user = userRepository.getUser(userId);
        
        sharingRepository.addSendTrip(userId, receiver, trip, auth);
        sharingRepository.addReceiveTrip(receiverId, user.getNickname(), trip, auth);
	}
	
	// 공유된 여행 권한 삭제
	public void deleteAuth(String userId, String receiver, String trip, String auth) {
        String receiverId = userRepository.getIdByNickname(receiver);

		User user = userRepository.getUser(userId);
        
        Long removedSend = sharingRepository.removeSendTrip(userId, receiver, trip, auth);
        if (removedSend == 0) {
            throw new IllegalArgumentException("삭제 실패 (보낸 목록)");
        }

        Long removedReceive = sharingRepository.removeReceiveTrip(receiverId, user.getNickname(), trip, auth);
        if (removedReceive == 0) {
            throw new IllegalArgumentException("삭제 실패 (받은 목록)");
        }
	}
	
	// 해당 여행의 권한 목록
	public List<SharingDto> getAuth(String userId, String trip) {
		// 권한 목록 추출
		Set<String> authSet = sharingRepository.getSendTrips(userId, trip);
		User user = userRepository.getUser(userId);
		
		if (authSet == null || authSet.isEmpty()) {
            throw new ResourceNotFoundException("여행 일정을 공유한 사람이 없습니다.");
        }
		
		List<SharingDto> sharingDtos = new ArrayList<>(authSet.size());
		for(String auth : authSet) {
			String[] str = auth.split(":");
			sharingDtos.add(new SharingDto(user.getNickname(), str[0], str[1], str[2]));
        }
		
		return sharingDtos;
	}
	
	// 공유 받은 여행 목록
	public List<SharingDto> getSharedTrip(String userId) {
		// 여행 목록 추출
		Set<String> tripSet = sharingRepository.getReceivedTrips(userId);
		User user = userRepository.getUser(userId);
		
		if (tripSet == null || tripSet.isEmpty()) {
            throw new ResourceNotFoundException("공유된 여행이 없습니다.");
        }
		
		List<SharingDto> sharingDtos = new ArrayList<>(tripSet.size());
		for(String trip : tripSet) {
			String[] str = trip.split(":");
			sharingDtos.add(new SharingDto(str[0], user.getNickname(), str[1], str[2]));
        }
		
		return sharingDtos;
	}
}
