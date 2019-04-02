package com.jiujun.voice.modules.apps.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.system.dao.ClientAppDao;
import com.jiujun.voice.modules.apps.system.domain.ClientApp;
import com.jiujun.voice.modules.apps.system.service.ClientAppService;

/**
 * @author Coody
 *
 */
@Service
public class ClientAppServiceImpl implements ClientAppService {

	@Resource
	ClientAppDao clientAppDao;

	@Override
	public List<ClientApp> getNextClientApps(Integer clientType, String packager, String currentVersion) {
		return clientAppDao.getNextClientApps(clientType, packager, currentVersion);
	}

	@Override
	public ClientApp getLastVersion(Integer clientType, String packager) {
		return clientAppDao.getLastVersion(clientType, packager);
	}

	@Override
	public List<String> getPackagers(Integer clientType) {
		return clientAppDao.getPackagers(clientType);
	}
}
