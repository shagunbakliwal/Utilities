ReferralDetails referralDetails = new ReferralDetails();
		Referral referral = new Referral();
		PropertyAccessor myAccessorForDirectFieldAccessReferralDetails = PropertyAccessorFactory
				.forDirectFieldAccess(referralDetails);

		StringBuilder sb = new StringBuilder();
		Method[] gettersAndSetters = referralVO.getClass().getMethods();

		for (int i = 0; i < gettersAndSetters.length; i++) {
			String methodName = gettersAndSetters[i].getName();
			try {
				if (methodName.startsWith("get")) {
					referralDetails.getClass()
							.getMethod(methodName.replaceFirst("get", "set"), gettersAndSetters[i].getReturnType())
							.invoke(referralDetails, gettersAndSetters[i].invoke(referralVO, null));
				} else if (methodName.startsWith("is")) {
					referralDetails.getClass()
							.getMethod(methodName.replaceFirst("is", "set"), gettersAndSetters[i].getReturnType())
							.invoke(referralDetails, gettersAndSetters[i].invoke(referralVO, null));
				}

			} catch (NoSuchMethodException | IllegalArgumentException e) {
			}
		}
		
		
		for (int i = 0; i < gettersAndSetters.length; i++) {
			String methodName = gettersAndSetters[i].getName();
			try {
				if (methodName.startsWith("get")) {
					referral.getClass()
							.getMethod(methodName.replaceFirst("get", "set"), gettersAndSetters[i].getReturnType())
							.invoke(referral, gettersAndSetters[i].invoke(referralVO, null));
				} else if (methodName.startsWith("is")) {
					referral.getClass()
							.getMethod(methodName.replaceFirst("is", "set"), gettersAndSetters[i].getReturnType())
							.invoke(referral, gettersAndSetters[i].invoke(referralVO, null));
				}

			} catch (NoSuchMethodException | IllegalArgumentException e) {
			}
		}
		referralDetails.setReferral(referral);
